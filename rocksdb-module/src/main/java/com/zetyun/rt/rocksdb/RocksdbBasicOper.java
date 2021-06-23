package com.zetyun.rt.rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

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

                List<String> keys = new ArrayList<>();
                for (Integer start = 0; start < 100000; start++) {
                    keys.add(start.toString());
                }

                keys.forEach(key -> {
                    try {
                        byte[] keyBytes = key.getBytes();
                        byte[] value = rocksDB.get(keyBytes);
                        if (value == null) {
                            rocksDB.put(keyBytes, keyBytes);
                        }
                    } catch (RocksDBException dbExe) {
                    }
                        }
                );

                // 1. Method => get all values by keys
                /*
                List<byte[]> keysInBytes = keys
                        .stream()
                        .map(x -> x.getBytes())
                        .collect(Collectors.toList());
                List<byte[]> queryValues = rocksDB.multiGetAsList(keysInBytes);
                System.out.println("============Using MultiGetAsList===================");
                queryValues.stream().map(x -> new String(x)).forEach(x -> {
                    System.out.println("Current Input Key :[" + x + "], getValues[" + x + "]");
                });
                 */

                // 2. Method => Using Iterator
                RocksIterator iter = rocksDB.newIterator();
                List<String>  outputValues = new ArrayList<>();
                System.out.println("============Using Iterator===================");
                for (iter.seekToFirst(); iter.isValid(); iter.next()) {
                    String value = new String(iter.value());
                    outputValues.add(value);
                }
                outputValues.forEach(x -> {
                    System.out.println("Current Input Key :[" + x + "], getValues[" + x + "]");
                });

                // 3. do delete
                keys.forEach(key -> {
                            try {
                                byte[] keyBytes = key.getBytes();
                                rocksDB.delete(keyBytes);
                            } catch (RocksDBException dbExe) {
                            }
                        }
                );
                // 4. Method => Using Iterator
                RocksIterator iterAfterDel = rocksDB.newIterator();
                List<String>  outputValuesAfterDel = new ArrayList<>();
                System.out.println("============Using Iterator After Delete===================");
                for (iterAfterDel.seekToFirst(); iterAfterDel.isValid(); iterAfterDel.next()) {
                    String value = new String(iterAfterDel.value());
                    outputValuesAfterDel.add(value);
                }
                outputValuesAfterDel.forEach(x -> {
                    System.out.println("Current Input Key :[" + x + "], getValues[" + x + "]");
                });
            }
        }
    }
}
