package com.zetyun.rt.rocksdb;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class HashMapMain {
    public static void main(String[] args) {
        HashMap<String, Long> hashMap = new HashMap<>();

        IntStream.range(1, 100)
                .forEach(x -> {
                    hashMap.computeIfAbsent(String.valueOf(x), new Function<String, Long>() {
                        @Override
                        public Long apply(String s) {
                            return Long.valueOf(x);
                        }
                    });
                });

        IntStream.range(1, 100)
                .forEach(x -> {
                    hashMap.computeIfPresent(String.valueOf(x), new BiFunction<String, Long, Long>() {
                        @Override
                        public Long apply(String s, Long aLong) {
                            return aLong * 2;
                        }
                    });
                });

        for (Map.Entry<String, Long> entry: hashMap.entrySet()) {
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }
}
