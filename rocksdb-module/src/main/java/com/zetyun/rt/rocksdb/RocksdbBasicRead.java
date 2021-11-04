package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.nio.charset.StandardCharsets;

public class RocksdbBasicRead {

    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        try(final Options options = new Options().setCreateIfMissing(true).setInfoLogLevel(InfoLogLevel.DEBUG_LEVEL)) {
            try (final RocksDB rocksDB = RocksDB.openReadOnly(options, db_path)) {
                System.out.println("LastSequence: " + rocksDB.getLatestSequenceNumber());
                RocksIterator it = rocksDB.newIterator();
                it.seekToFirst();
                while(it.isValid()) {
                    System.out.println("Key: " + new String(it.key()) + "  Value: " + new String(it.value()));
                    it.next();
                }
            }
        }
    }
}
