package com.zetyun.rt;

import com.zetyun.rt.network.Server;
import com.zetyun.rt.replicator.DBRole;
import com.zetyun.rt.replicator.ReplicatedDB;
import com.zetyun.rt.replicator.RocksdbReplicator;
import com.zetyun.rt.utils.SystemOpUtils;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Master{
    final static Logger logger = LoggerFactory.getLogger(Master.class);
    public static void main(String[] argv) throws RocksDBException, InterruptedException {
        RocksDB masterDb = SystemOpUtils.cleanUpOpenDB("/tmp/db_master");
        RocksdbReplicator replicator = RocksdbReplicator.getInstance();
        String dbName = "db";
        Server serverNode = new Server(9001);
        serverNode.start();
        replicator.addDB(dbName, masterDb, DBRole.MASTER, "");
        ReplicatedDB masterReplicatorDB = replicator.getReplicateDB(dbName);
        for (int j = 0; j < 10; j++) {
            WriteBatch batch = new WriteBatch();
            for (Integer i = 0; i < 3000; i++) {
                batch.put(i.toString().getBytes(), i.toString().getBytes());
            }
            masterReplicatorDB.write(new WriteOptions(), batch);
        }
        serverNode.join();
    }
}
