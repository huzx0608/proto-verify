package com.zetyun.rt.replicator;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rocksdb.RocksDB;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import java.util.HashMap;

public class RocksdbReplicator {

    final static Logger logger = LoggerFactory.getLogger(RocksdbReplicator.class);

    private HashMap<String, ReplicatedDB> replicatedDBMap = new HashMap<>();
    private RocksdbReplicator() {
    }

    public static RocksdbReplicator getInstance() {
        return RocksdbReplicatorHold.replicator;
    }

    private static class RocksdbReplicatorHold {
        private static final RocksdbReplicator replicator = new RocksdbReplicator();
    }

    public ReturnCode addDB(final String dbName,
                            RocksDB      dbInstance,
                            final DBRole dbRole,
                            final String upStreamAddr) {
        /**
         * 1. check hash map by dbname, and add Instance to HashMap
         * 2. if the role of db is SLAVE, pullFromUpstream
         * 3. add db to cleaner, start a thread to clean the db directory
         */
        if (this.replicatedDBMap.get(dbName) != null) {
            logger.warn("Receive addDB Request, but DB:{} has added", dbName);
            return ReturnCode.DB_PRE_EXIST;
        }

        WriteOptions writeOptions = new WriteOptions();

        ReplicatedDB replicatedDB = new ReplicatedDB(
                dbName,
                dbInstance,
                dbRole,
                upStreamAddr,
                writeOptions
        );
        this.replicatedDBMap.put(dbName, replicatedDB);

        if (dbRole == DBRole.SLAVE) {
            replicatedDB.pullFromUpStream();
            logger.info("Receive addDB request: dbName:{}. Start as Slave, pull from Upstream:{}",
                    dbName,
                    upStreamAddr);
        } else {
            logger.info("Receive addDB request: dbName:{}. Start as Master", dbName);
        }
        return ReturnCode.OK;
    }

    public ReturnCode removeDB(final String dbName) {
        /**
         * 1. remove db from HashMap
         * 2. check the resource has already released
         */
        if (this.replicatedDBMap.get(dbName) == null) {
            logger.warn("Receive RemoteDB Request, but db:{} Not Exist", dbName);
            return ReturnCode.DB_NOT_FOUND;
        }
        this.replicatedDBMap.remove(dbName);
        // 2. todo check to release the resource or not
        return ReturnCode.OK;
    }

    public Long write(final String dbName, WriteBatch batch) {
        /**
         * call replicator db to write the batch, and return the latest sequence No.
         */
        ReplicatedDB replicatedDB = this.replicatedDBMap.get(dbName);
        if (replicatedDB == null) {
            logger.error("Receive Write Request, but db:{} Not Exist", dbName);
            return -1L;
        }
        return replicatedDB.write(batch);
    }

    public ReplicatedDB getReplicateDB(String dbName) {
        return this.replicatedDBMap.get(dbName);
    }
}
