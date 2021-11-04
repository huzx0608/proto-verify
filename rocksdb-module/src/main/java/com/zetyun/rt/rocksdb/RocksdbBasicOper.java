package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.nio.charset.StandardCharsets;


public class RocksdbBasicOper {

    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        try(final Options options = new Options().setCreateIfMissing(true).setInfoLogLevel(InfoLogLevel.DEBUG_LEVEL)) {
            final RocksDB instance1 = RocksDB.open(options, db_path);

            WriteBatch batch = new WriteBatch();
            WriteOptions writeOption = new WriteOptions();
            for (int i = 0; i < 100; i++) {
                String key = String.format("%08d", i);
                String val = String.format("%016d", i*1000);
                batch.put(key.getBytes(StandardCharsets.UTF_8), val.getBytes(StandardCharsets.UTF_8));
                if (i != 0 && i % 10 == 0) {
                    instance1.write(writeOption, batch);
                    instance1.flush(new FlushOptions());
                    batch.clear();
                }
                System.out.println(key+"|"+val);
            }
            instance1.flush(new FlushOptions());
        }
    }
}
