package com.zetyun.rt;

import com.zetyun.rt.replicator.DBRole;
import com.zetyun.rt.replicator.ReplicatedDB;
import com.zetyun.rt.replicator.RocksdbReplicator;
import com.zetyun.rt.utils.SystemOpUtils;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slave {
    final static Logger logger = LoggerFactory.getLogger(Slave.class);
    public static void main(String[] argv) throws RocksDBException, InterruptedException {
        RocksDB slaveDB = SystemOpUtils.cleanUpOpenDB("/tmp/db_slave");
        RocksdbReplicator replicator = RocksdbReplicator.getInstance();
        String dbName = "db";
        replicator.addDB(dbName, slaveDB, DBRole.SLAVE, "localhost:9001");
        ReplicatedDB slaveReplicateDB = replicator.getReplicateDB(dbName);
        slaveReplicateDB.pullFromUpStream();
    }
}
