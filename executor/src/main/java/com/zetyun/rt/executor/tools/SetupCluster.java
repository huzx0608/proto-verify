package com.zetyun.rt.executor.tools;

import org.apache.helix.manager.zk.ZKHelixAdmin;
import org.apache.helix.manager.zk.ZNRecordSerializer;
import org.apache.helix.model.IdealState.RebalanceMode;
import org.apache.helix.model.InstanceConfig;
import org.apache.helix.model.StateModelDefinition;
import org.apache.helix.tools.StateModelConfigGenerator;
import org.apache.helix.zookeeper.impl.client.ZkClient;

import static com.zetyun.rt.config.ClusterCfgConst.*;

public class SetupCluster {
  public static final int DEFAULT_PARTITION_NUMBER = 1;

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err
          .println("USAGE: java SetupCluster zookeeperAddress(e.g. localhost:2181) numberOfNodes");
      System.exit(1);
    }

    final String zkAddr = args[0];
    final int numNodes = Integer.parseInt(args[1]);
    final String clusterName = DEFAULT_CLUSTER_NAME;

    ZkClient zkclient = null;
    try {
      zkclient =
          new ZkClient(zkAddr, ZkClient.DEFAULT_SESSION_TIMEOUT,
              ZkClient.DEFAULT_CONNECTION_TIMEOUT, new ZNRecordSerializer());
      ZKHelixAdmin admin = new ZKHelixAdmin(zkclient);

      // add cluster
      admin.addCluster(clusterName, true);

      // add state model definition
      StateModelConfigGenerator generator = new StateModelConfigGenerator();
      admin.addStateModelDef(clusterName, DEFAULT_STATE_MODEL,
          new StateModelDefinition(generator.generateConfigForMasterSlave()));
      // addNodes
      for (int i = 0; i < numNodes; i++) {
        String port = "" + (12001 + i);
        String serverId = "172.20.3.18_" + port;
        InstanceConfig config = new InstanceConfig(serverId);
        config.setHostName("172.20.3.18");
        config.setPort(port);
        config.setInstanceEnabled(true);
        admin.addInstance(clusterName, config);
      }
      // add resource "repository" which has 1 partition
      String resourceName = DEFAULT_RESOURCE_NAME;
      admin.addResource(clusterName, resourceName, DEFAULT_PARTITION_NUMBER, DEFAULT_STATE_MODEL,
          RebalanceMode.SEMI_AUTO.toString());
      admin.rebalance(clusterName, resourceName, 3);

    } finally {
      if (zkclient != null) {
        zkclient.close();
      }
    }
  }
}
