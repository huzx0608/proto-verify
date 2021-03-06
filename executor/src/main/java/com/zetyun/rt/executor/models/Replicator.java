package com.zetyun.rt.executor.models;

import org.apache.helix.NotificationContext;
import org.apache.helix.model.ExternalView;
import org.apache.helix.model.InstanceConfig;
import org.apache.helix.spectator.RoutingTableProvider;
import org.apache.helix.zookeeper.datamodel.ZNRecord;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Replicator extends RoutingTableProvider {
  private InstanceConfig currentMasterConfig;
  private final InstanceConfig localInstanceConfig;

  private final String partition;
  private final String resourceName;
  AtomicBoolean isReplicationInitiated;
  AtomicBoolean isReplicationStarted;
  RsyncInvoker rsyncInvoker;
  private ChangeLogProcessor processor;
  private FileSystemWatchService watchService;
  private ChangeLogReader reader;

  public void setRsyncInvoker(RsyncInvoker rsyncInvoker) {
    this.rsyncInvoker = rsyncInvoker;
  }

  public Replicator(InstanceConfig localInstanceConfig, String resourceName, String partition) {
    this.localInstanceConfig = localInstanceConfig;
    this.resourceName = resourceName;
    this.partition = partition;
    isReplicationInitiated = new AtomicBoolean(false);
    isReplicationStarted = new AtomicBoolean(false);

  }

  public void start() throws Exception {
    // System.out.println("Starting replication for ");
    isReplicationInitiated.set(true);

    List<InstanceConfig> instances = getInstances(resourceName, partition, "MASTER");
    if (instances.size() > 0) {
      if (instances.size() == 1) {
        InstanceConfig newMasterConfig = instances.get(0);
        String master = newMasterConfig.getInstanceName();
        if (currentMasterConfig == null
            || !master.equalsIgnoreCase(currentMasterConfig.getInstanceName())) {
          System.out.println("Found new master:" + newMasterConfig.getInstanceName());
          if (currentMasterConfig != null) {
            stop();
          }
          currentMasterConfig = newMasterConfig;
          startReplication(currentMasterConfig);
        } else {
          System.out.println("Already replicating from " + master);
        }
      } else {
        System.out.println("Invalid number of masters found:" + instances);
      }
    } else {
      System.out.println("No master found");
    }
  }

  public void startReplication(InstanceConfig masterInstanceConfig) throws Exception {
    String remoteHost = masterInstanceConfig.getHostName();
    String remoteChangeLogDir = masterInstanceConfig.getRecord().getSimpleField("change_log_dir");
    String remoteFilestoreDir = masterInstanceConfig.getRecord().getSimpleField("file_store_dir");

    String localChangeLogDir = localInstanceConfig.getRecord().getSimpleField("change_log_dir");
    String localFilestoreDir = localInstanceConfig.getRecord().getSimpleField("file_store_dir");
    String localcheckpointDir = localInstanceConfig.getRecord().getSimpleField("check_point_dir");
    // setup rsync for the change log directory
    setupRsync(remoteHost, remoteChangeLogDir, localChangeLogDir);
    reader = new ChangeLogReader(localChangeLogDir);
    watchService = new FileSystemWatchService(localChangeLogDir, reader);
    processor =
        new ChangeLogProcessor(reader, remoteHost, remoteFilestoreDir, localFilestoreDir,
            localcheckpointDir);
    watchService.start();
    processor.start();
    isReplicationStarted.set(true);
  }

  private void setupRsync(String remoteHost, String remoteBaseDir, String localBaseDir)
      throws Exception {
    rsyncInvoker = new RsyncInvoker(remoteHost, remoteBaseDir, localBaseDir);
    boolean started = rsyncInvoker.runInBackground();
    if (started) {
      System.out.println("Rsync thread started in background");
    } else {
      throw new Exception("Unable to start rsync thread");
    }
  }

  public void stop() {
    if (isReplicationStarted.get()) {
      System.out.println("Stopping replication from current master:"
          + currentMasterConfig.getInstanceName());
      rsyncInvoker.stop();
      watchService.stop();
      processor.stop();
    }
    isReplicationInitiated.set(false);
  }

  @Override
  public void onExternalViewChange(List<ExternalView> viewList, NotificationContext context) {
    super.onExternalViewChange(viewList, context);

    if (isReplicationInitiated.get()) {
      try {
        start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    InstanceConfig localInstanceConfig = new InstanceConfig("localhost_12001");
    ZNRecord record = localInstanceConfig.getRecord();
    record.setSimpleField("change_log_dir", "data/localhost_12001/translog");
    record.setSimpleField("file_store_dir", "data/localhost_12001/filestore");
    record.setSimpleField("check_point_dir", "data/localhost_12001/checkpoint");
    InstanceConfig masterInstanceConfig = new InstanceConfig("localhost_12001");
    record = masterInstanceConfig.getRecord();
    record.setSimpleField("change_log_dir", "data/localhost_12000/translog");
    record.setSimpleField("file_store_dir", "data/localhost_12000/filestore");
    record.setSimpleField("check_point_dir", "data/localhost_12000/checkpoint");
    Replicator replicator = new Replicator(localInstanceConfig, "resource", "partition");
    replicator.startReplication(masterInstanceConfig);
  }

}
