package com.zetyun.rt.replicator;

import com.zetyun.rt.network.Client;
import com.zetyun.rt.network.Server;
import com.zetyun.rt.replicator.Update;
import com.zetyun.rt.utils.AddressUtils;
import com.zetyun.rt.utils.BytesUtils;
import lombok.Getter;
import lombok.Setter;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

@Getter
@Setter
public class ReplicatedDB {
    final static Logger logger = LoggerFactory.getLogger(ReplicatedDB.class);
    private static Long defaultMaxWaitMs = 1000L;
    private static int  defaultMaxUpdates = 100;
    private static int  defaultDelayInterval = 100;

    private String  dbName;
    private RocksDB dbInstance;
    private DBRole  dbRole;
    private String  upStreamIp;
    private int     upStreamPort;
    private WriteOptions writeOptions;
    private Client thriftClient;
    private Server thriftServer;
    private Long   lastSeqNo;

    public ReplicatedDB(final String dbName,
                        RocksDB  dbInstance,
                        DBRole   dbRole,
                        final String upStreamAddr,
                        WriteOptions writeOptions) {
        this.dbName = dbName;
        this.dbInstance = dbInstance;
        this.dbRole = dbRole;
        this.writeOptions = writeOptions;
        this.lastSeqNo = 0L;
        if (dbRole == DBRole.SLAVE) {
            AddressUtils address = new AddressUtils(upStreamAddr);
            this.upStreamIp = address.getAddress();
            this.upStreamPort = address.getPort();
            this.upStreamIp = "localhost";
            this.thriftClient = new Client(upStreamIp, upStreamPort);
        } else {
            this.thriftServer = new Server(upStreamPort);
        }
        doInit();
    }

    private void doInit() {
        try {
            if (dbRole == DBRole.SLAVE) {
                this.thriftClient.initConnection();
            } else {
                this.thriftServer.start();
            }
        } catch (Exception exp) {
            logger.error("Init doInit DB with role:" + dbRole.toString()
                    + " catch exception" + exp.toString(), exp);
            exp.printStackTrace();
        }
    }

    // Similar to rocksdb::DB::Write(). Only two differences:
    // 1) seq_no, it will be filled with a sequence # after applying the
    // updates. This is useful to implement read-after-write consistency at
    // higher level.
    // 2) WRITE_TO_SLAVE will be thrown if this is a SLAVE db.
    // 3) WAIT_SLAVE_TIMEOUT will be thrown if replication mode 1 and 2 is
    // enabled, and no slave gets back to us in time. In this case, the update
    // is guaranteed to be committed to Master. Slaves may or may not have got
    // the update.
    public Long write(WriteOptions writeOpt, WriteBatch batch) {
        //
        /**
         * 1. check the role of db(must be master).
         */
        if (dbRole == DBRole.SLAVE) {
            logger.warn("Current Role is Slave, it cannot be written, dbName:{}", dbName);
            return -1L;
        }

        Long latestSeqNo = 0L;
        try {
            /**
             * 2. append current timestamp to batch and write batch.
             */
            Long currentMs = System.currentTimeMillis();
            byte[] currentMSBytes = BytesUtils.longToBytes(currentMs);
            batch.putLogData(currentMSBytes);
            dbInstance.write(writeOpt, batch);
            dbInstance.flush(new FlushOptions());

            /**
             * 3. get the latest sequence number and return.
             */
            latestSeqNo = dbInstance.getLatestSequenceNumber();

            /**
             * 4. check current replay mode, will block or continue
             */
            // todo
            /**
             * 5. add the write records to local cache
             */
            // todo
        } catch (RocksDBException ex) {
            logger.error("Persist data to rocksdb, catch exception:" + ex.toString(), ex);
        }
        return latestSeqNo;
    }


    public void pullFromUpStream() {
        // 1. check the role of db, must be slave
        if (dbRole != DBRole.SLAVE) {
            logger.warn("Current Role is master, but start pullFromUpStream Request");
            return;
        }

        /**
         * 2. construct the replication request and send request
         * 2.1 get current sequence number of db
         * 2.2 construct the request
         * 2.3 parse the response and get the data
         * 2.4 loop re pull from upstream
         */
        com.zetyun.rt.replicator.ReplicateReq request = new com.zetyun.rt.replicator.ReplicateReq();
        request.setDbName(dbName);
        request.setSeqNo(dbInstance.getLatestSequenceNumber() + 1);
        request.setMaxWaitMs(defaultMaxWaitMs);
        request.setMaxUpdates(defaultMaxUpdates);

        com.zetyun.rt.replicator.ReplicateRsp response = thriftClient.sendFetchRequest(request);

        Long updateTimeStamp = 0L;
        try {
            for (Update update : response.getUpdates()) {
                System.out.println("===> Receive response with updates:" + update.getTimestamp());
                updateTimeStamp = update.getTimestamp();
                WriteBatch writeBatch = new WriteBatch(update.getRawData());
                writeBatch.putLogData(BytesUtils.longToBytes(updateTimeStamp));
                dbInstance.write(writeOptions, writeBatch);
                dbInstance.flush(new FlushOptions());
            }
            if (defaultDelayInterval > 100) {
                Thread.sleep(defaultDelayInterval);
            }
            this.pullFromUpStream();
        } catch (RocksDBException exp) {
            exp.printStackTrace();
            logger.error("WriteBatch: timeStamp:" + updateTimeStamp
                    + ", catch Rocksdb Exception:" + exp.toString(), exp);
        } catch (InterruptedException exp) {
            exp.printStackTrace();
            logger.error("WriteBatch: timeStamp:" + updateTimeStamp
                    + ", catch Interrupt Exception:" + exp.toString(), exp);
        }
    }

    /**
     * Reset the upstream IP after querying for latest leader from helix.
     */
    public void resetUpStream() {
    }

    public com.zetyun.rt.replicator.ReplicateRsp handelReplicator(com.zetyun.rt.replicator.ReplicateReq req) {
        /**
         * 1. parse the replicate request
         * 2. call Rocksdb::GetUpdateSince to get data
         *   2.1 rethink whether exist a cache map to accelerator the cache map
         * 3. construct the response and return
         */
        return null;
    }
}
