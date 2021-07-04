package com.zetyun.rt.replicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

import java.util.concurrent.Executor;

@Getter
@Setter
public class ReplicatedDB {
    private String  dbName;
    private RocksDB dbInstance;
    private DBRole  dbRole;
    private String  upStreamAddr;
    private Executor executorPool;
    private WriteOptions writeOptions;

    public ReplicatedDB(final String dbName,
                        RocksDB  dbInstance,
                        Executor executor,
                        DBRole   dbRole,
                        final String upStreamAddr,
                        WriteOptions writeOptions) {
        this.dbName = dbName;
        this.dbInstance = dbInstance;
        this.executorPool = executor;
        this.dbRole = dbRole;
        this.upStreamAddr = upStreamAddr;
        this.writeOptions = writeOptions;
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
        }

        /**
         * 2. append current timestamp to batch and write batch.
         */

        /**
         * 3. get the latest sequence number and return.
         */

        /**
         * 4. check current replay mode, will block or continue
         */

        /**
         * 5. add the write records to local cache
         */
        return 0L;
    }


    public void pullFromUpStream() {
        // 1. check the role of db, must be slave
        /**
         * 2. construct the replication request and send request
         * 2.1 get current sequence number of db
         * 2.2 construct the request
         * 2.3 parse the response and get the data
         * 2.4 loop re pull from upstream
         */
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
