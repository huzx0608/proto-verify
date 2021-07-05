package com.zetyun.rt.utils;

public class BytesUtils {

    public static long bytesToLong(byte[] bytes) {
        Long longVar = Long.valueOf(new String(bytes)).longValue();
        return longVar;
    }

    public static byte[] longToBytes(Long value) {
        byte[] bytes = String.valueOf(value).getBytes();
        return bytes;
    }
}
