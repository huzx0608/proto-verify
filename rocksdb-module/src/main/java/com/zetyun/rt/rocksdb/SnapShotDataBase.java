package com.zetyun.rt.rocksdb;

import org.rocksdb.*;


public class SnapShotDataBase {

    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        final Options options = new Options().setCreateIfMissing(true);
        final RocksDB rocksDB = RocksDB.open(options, db_path);

        rocksDB.put("huzx01".getBytes(), "huzx01".getBytes());
        rocksDB.put("huzx02".getBytes(), "huzx02".getBytes());
        rocksDB.put("huzx03".getBytes(), "huzx03".getBytes());
        rocksDB.flush(new FlushOptions());

        ReadOptions read_options = new ReadOptions();
        Snapshot snapshot = rocksDB.getSnapshot();
        read_options.setSnapshot(snapshot);
        rocksDB.put("huzx04".getBytes(), "abc01".getBytes());
        rocksDB.put("huzx05".getBytes(), "abc02".getBytes());
        rocksDB.put("huzx06".getBytes(), "abc02".getBytes());
        byte[] value01 = rocksDB.get(read_options, "huzx01".getBytes());
        System.out.println("1==>Current Result is ==>[" + new String(value01) + "]");

        read_options.setSnapshot(snapshot);
        byte[] value02 = rocksDB.get(read_options, "huzx04".getBytes());
        if (value02 != null) {
            System.out.println("2==>Current Result is ==>[" + new String(value02) + "]");
        } else {
            System.out.println("2==>Current Result is===> Null!" );
            System.out.println("2==>Current Result is ==>[" + new String(rocksDB.get("huzx01".getBytes())) + "]");
        }

        rocksDB.close();
    }
}
