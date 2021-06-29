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
@AllArgsConstructor
@NoArgsConstructor
public class ReplicatedDB {
    private String  dbName;
    private RocksDB dbInstance;
    private DBRole  dbRole;
    private String  upStreamAddr;
    private Executor executorPool;
    private Options  writeOptions;


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
        return 0L;
    }

    public void pullFromUpStream() {
    }

    public void resetUpStream() {
    }

    public void handleReplicateRequest() {
    }
}
