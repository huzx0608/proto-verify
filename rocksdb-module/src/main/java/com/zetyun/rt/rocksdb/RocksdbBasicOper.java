package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.util.ArrayList;
import java.util.List;

public class RocksdbBasicOper {

    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        // db_path = args[0];
        try(final Options options = new Options().setCreateIfMissing(true)) {
            try (final RocksDB rocksDB = RocksDB.open(options, db_path)) {

                WriteBatch writeBatch = new WriteBatch();
                for (Integer start = 0; start < 10; start++) {
                    String strKey = start.toString();
                    writeBatch.put(strKey.getBytes(), strKey.getBytes());
                }

                Long currentTime = System.currentTimeMillis();
                writeBatch.putLogData(currentTime.toString().getBytes());
                rocksDB.write(new WriteOptions(), writeBatch);

                // 2. Method => Using Iterator
                TransactionLogIterator txIter = rocksDB.getUpdatesSince(1);
                if (txIter.isValid()) {
                    TransactionLogIterator.BatchResult result = txIter.getBatch();
                    WriteBatch batch = result.writeBatch();
                    System.out.println("Batch size:" + batch.getDataSize() + ", " + batch.count());
                    System.out.println("Batch size:" + writeBatch.getDataSize() + ", " + writeBatch.count());
                    byte[] bytes = batch.data();
                    System.out.println("Huzx=>" + new String(bytes));
                }
                // RocksIterator iter = rocksDB.newIterator();
                // System.out.println("============Using Iterator===================");
                /*
                for (iter.seekToFirst(); iter.isValid(); iter.next()) {
                    System.out.println("Current Input Key :[" + new String(iter.key()) + "], Value[" + new String(iter.value()) + "]");
                }
                */
            }
        }
    }
}
