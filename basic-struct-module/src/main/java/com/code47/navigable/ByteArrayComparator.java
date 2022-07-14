package com.code47.navigable;

public class ByteArrayComparator {
    public static int compare(byte[] bytes1, byte[] bytes2) {
        int n = Math.min(bytes1.length, bytes2.length);
        for (int i = 0; i < n; i++) {
            if (bytes1[i] == bytes2[i]) {
                continue;
            }
            return (bytes1[i] & 0xFF) - (bytes2[i] & 0xFF);
        }
        return bytes1.length - bytes2.length;
    }
}
