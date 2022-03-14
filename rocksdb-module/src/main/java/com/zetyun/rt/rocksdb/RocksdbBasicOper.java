package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.nio.charset.StandardCharsets;


public class RocksdbBasicOper {

    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        String startKey = "";
        String endKey = "";
        try(final Options options = new Options().setCreateIfMissing(true).setInfoLogLevel(InfoLogLevel.DEBUG_LEVEL)) {
            options.setMaxTotalWalSize(4 * 1024 * 1024 * 1024L);
            options.setKeepLogFileNum(1);
            options.setWalSizeLimitMB(1024);
            final RocksDB instance1 = RocksDB.open(options, db_path);

            WriteBatch batch = new WriteBatch();
            WriteOptions writeOption = new WriteOptions();
            for (int i = 0; i < 100; i++) {
                String key = String.format("%08d", i);
                if (i == 0) {
                    startKey = key;
                }

                if (i == 99) {
                    endKey = key;
                }
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

            System.out.println("===========wait to delete range=========");

            Thread.sleep(1000);

            System.out.println("===========Start to delete range=========");
            instance1.deleteRange(startKey.getBytes(StandardCharsets.UTF_8), endKey.getBytes(StandardCharsets.UTF_8));
            // List<byte[]> deleteRangeScopy = Arrays.asList(startKey.getBytes(StandardCharsets.UTF_8), endKey.getBytes(StandardCharsets.UTF_8));
            // instance1.deleteFilesInRanges(instance1.getDefaultColumnFamily(), deleteRangeScopy, true);
            instance1.compactRange();
            // instance1.flush(new FlushOptions());
            // instance1.compactRange();
        }
    }
}
