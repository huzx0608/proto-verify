package com.zetyun.rt.coodinator.main;

import org.apache.helix.HelixManager;
import org.apache.helix.controller.HelixControllerMain;

import static com.zetyun.rt.config.ClusterCfgConst.DEFAULT_CLUSTER_NAME;

public class CoordinatorMain {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("USAGE: java StartClusterManager zookeeperAddress (e.g. localhost:2181)");
      System.exit(1);
    }

    final String clusterName = DEFAULT_CLUSTER_NAME;
    final String zkAddr = args[0];

    try {
      final HelixManager manager =
          HelixControllerMain.startHelixController(zkAddr, clusterName, null,
              HelixControllerMain.STANDALONE);

      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          System.out.println("Shutting down cluster manager: " + manager.getInstanceName());
          manager.disconnect();
        }
      });

      Thread.currentThread().join();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
