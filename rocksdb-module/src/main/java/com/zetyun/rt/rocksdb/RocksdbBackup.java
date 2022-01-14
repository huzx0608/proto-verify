package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

public class RocksdbBackup {
    private static String db_path = "./rocksdb-module/data/rocksdb-data";
    private static String backup_path = "./rocksdb-module/data/rocksdb-backups/";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        final Options options = new Options().setCreateIfMissing(true);
        final RocksDB rocksDB = RocksDB.open(options, db_path);

        Long startWriteTs = System.currentTimeMillis();;
        IntStream.range(0, 1000000).forEach(x -> {
            try {
                String key = String.format("%100d", x);
                String value = key;
                for (int i = 0; i < 100; i++) {
                    value += key;
                }
                rocksDB.put(key.getBytes(), value.getBytes(StandardCharsets.UTF_8));
            } catch (RocksDBException rocksDBException) {
                rocksDBException.printStackTrace();
            }
        });
        rocksDB.flush(new FlushOptions());
        Long endWriteTs = System.currentTimeMillis();;
        System.out.println("Huzx=> Write and flush total time cost:" + (endWriteTs - startWriteTs));

        Thread.sleep(10000);

        Long backupStartTs = System.currentTimeMillis();;
        System.out.println("Huzx=> Write and flush total time cost:" + (endWriteTs - startWriteTs));
        BackupableDBOptions backupableDBOptions = new BackupableDBOptions(backup_path);
        BackupEngine backupEngine = BackupEngine.open(Env.getDefault(), backupableDBOptions);
        backupEngine.createNewBackup(rocksDB, true);
        Long backupEndTs = System.currentTimeMillis();;
        System.out.println("Huzx=> backup total time cost:" + (backupEndTs - backupStartTs));

        rocksDB.put("Huzx01".getBytes(StandardCharsets.UTF_8), "huzx01".getBytes());
        backupEngine.createNewBackup(rocksDB, true);
        Long backupEndTs02 = System.currentTimeMillis();;
        System.out.println("Huzx2=> backup total time cost:" + (backupEndTs02 - backupStartTs));

        List<BackupInfo> backupInfoList = backupEngine.getBackupInfo();
        for (BackupInfo info: backupInfoList) {
            System.out.println(info);
        }
    }
}
