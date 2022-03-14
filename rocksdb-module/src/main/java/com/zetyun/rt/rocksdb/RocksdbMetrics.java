package com.zetyun.rt.rocksdb;

import org.rocksdb.*;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;


public class RocksdbMetrics {
    private static String db_path = "./rocksdb-module/data/rocksdb-data";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
        String startKey = "";
        String endKey = "";
        final Options options = new Options().setCreateIfMissing(true).setInfoLogLevel(InfoLogLevel.DEBUG_LEVEL);
        RocksDB.destroyDB(db_path, options);
        final RocksDB rocksDB = RocksDB.open(options, db_path);

        WriteBatch batch = new WriteBatch();
        WriteOptions writeOption = new WriteOptions();
        int recordCnt = 500000;
        for (int i = 0; i < recordCnt; i++) {
            String key = String.format("%08d", i);
            if (i == 0) {
                startKey = key;
            }

            if (i == recordCnt - 1) {
                endKey = key;
            }
            String val = String.format("%016d", i * 1000);
            batch.put(key.getBytes(StandardCharsets.UTF_8), val.getBytes(StandardCharsets.UTF_8));
            if (i != 0 && i % 1000 == 0) {
                rocksDB.write(writeOption, batch);
            }
        }
        rocksDB.write(writeOption, batch);
        rocksDB.flush(new FlushOptions());

        final String start = startKey;
        final String end = endKey;
        int times = 1000;
        Long startTime1 = System.nanoTime();
        IntStream.range(1, times).forEach(x -> {
            ApproximateKVStats stats = getApproximateStat(rocksDB,
                    start.getBytes(StandardCharsets.UTF_8),
                    end.getBytes(StandardCharsets.UTF_8));
            if (x == 1) {
                System.out.println("Index:" + x + ", stat:" + stats.toString());
            }
        });
        Long endTime1 = System.nanoTime();
        Long totalTimes1 = endTime1 - startTime1;

        Long startTime2 = System.nanoTime();
        IntStream.range(1, times).forEach(x -> {
            ApproximateKVStats stats = null;
            try {
                stats = getApproximateStatByAPI(rocksDB,
                        start.getBytes(StandardCharsets.UTF_8),
                        end.getBytes(StandardCharsets.UTF_8));
            } catch (RocksDBException e) {
                e.printStackTrace();
            }
            if (x == 1) {
                System.out.println("Index:" + x + ", stat:" + stats.toString());
            }
        });
        Long endTime2 = System.nanoTime();
        Long totalTimes2 = endTime2 - startTime2;

        ApproximateKVStats stats1 = getApproximateStat(
                rocksDB,
                start.getBytes(StandardCharsets.UTF_8),
                end.getBytes(StandardCharsets.UTF_8));
        System.out.println("Method1=> Using Iterator====> TotalRecordCnt=>"
                + recordCnt + ", IteratorCnt:"
                + times + ", TotalCost:"
                + (totalTimes1 / 1000 / 1000) + "ms, AvgCost:"
                + (totalTimes1 / times / 1000 / 1000) + "ms, DBStat:"
                + stats1
        );

        ApproximateKVStats stats2 = getApproximateStatByAPI(
                rocksDB,
                start.getBytes(StandardCharsets.UTF_8),
                end.getBytes(StandardCharsets.UTF_8));

        System.out.println("Method2=> Using RocksAPI====> TotalRecordCnt=>"
                + recordCnt + ", IteratorCnt:"
                + times + ", TotalCost:"
                + (totalTimes2 * 1.0 / 1000 / 1000) + "ms, AvgCost:"
                + (totalTimes2 * 1.0 / times / 1000 / 1000) + "ms, DBStat:"
                + stats2
        );

    }

    public static ApproximateKVStats getApproximateStatByAPI(final RocksDB db,
                                                             final byte[] start,
                                                             final byte[] end) throws RocksDBException {
        Long totalSize = Arrays.stream(db.getApproximateSizes(
                Collections.singletonList(new Range(new Slice(start), new Slice(end))),
                SizeApproximationFlag.INCLUDE_FILES,
                SizeApproximationFlag.INCLUDE_MEMTABLES
                )).sum();
        String totalCnt = db.getProperty(db.getDefaultColumnFamily(), "rocksdb.estimate-num-keys");
        return new ApproximateKVStats(Long.valueOf(totalCnt), totalSize);
    }

    public static ApproximateKVStats getApproximateStat(final RocksDB db, final byte[] startKey, final byte[] endKey) {
        // TODO This is a sad code, the performance is too damn bad
        final Snapshot snapshot = db.getSnapshot();
        Long approximateTotalBytes = 0L;

        try (final ReadOptions readOptions = new ReadOptions()) {
            readOptions.setSnapshot(snapshot);
            try (final RocksIterator it = db.newIterator(readOptions)) {
                if (startKey == null) {
                    it.seekToFirst();
                } else {
                    it.seek(startKey);
                }
                long approximateKeys = 0;
                for (; ; ) {
                    // The accuracy is 100, don't ask more
                    for (int i = 0; i < 100; i++) {
                        if (!it.isValid()) {
                            return new ApproximateKVStats(approximateKeys, approximateTotalBytes);
                        }
                        ++approximateKeys;
                        approximateTotalBytes += it.key().length;
                        approximateTotalBytes += it.value().length;
                        it.next();
                    }
                    if (endKey != null && compare(it.key(), endKey) >= 0) {
                        return new ApproximateKVStats(approximateKeys, approximateTotalBytes);
                    }
                }
            }
        } finally {
            // Nothing to release, rocksDB never own the pointer for a snapshot.
            snapshot.close();
            // The pointer to the snapshot is released by the database instance.
            db.releaseSnapshot(snapshot);
        }
    }

    public static int compare(final byte[] buffer1, final byte[] buffer2) {
        int offset1 = 0;
        int offset2 = 0;
        int length1 = buffer1.length;
        int length2 = buffer2.length;
        // short circuit equal case
        if (buffer1 == buffer2 && offset1 == offset2 && length1 == length2) {
            return 0;
        }
        // similar to Arrays.compare() but considers offset and length
        final int end1 = offset1 + length1;
        final int end2 = offset2 + length2;
        for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
            int a = buffer1[i] & 0xff;
            int b = buffer2[j] & 0xff;
            if (a != b) {
                return a - b;
            }
        }
        return length1 - length2;
    }
}
