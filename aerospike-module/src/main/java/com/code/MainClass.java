package com.code;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.WritePolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        int recordCnt = 10000;
        if (args.length >= 1) {
            recordCnt = Integer.valueOf(args[0]);
        }
        System.out.println("Insert record cnt is :" + recordCnt);
        AerospikeClient client = new AerospikeClient("172.20.3.28", 3000);
        WritePolicy writePolicy = new WritePolicy();
        writePolicy.setTimeout(10000000);

        List<String> columns = Arrays.asList("name", "age", "sex");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < recordCnt; i++) {
            Key key = new Key("test", "user", i);
            Bin bin1 = new Bin(columns.get(0), "huzx" + i);
            Bin bin2 = new Bin(columns.get(1), 100 + i);
            Bin bin3 = new Bin(columns.get(2), i % 2 == 0 ? "Man" : "Woman");
            client.put(writePolicy, key, bin1, bin2, bin3);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total Insert TimeCost:" + (endTime - startTime) + "ms, totalCnt:" + recordCnt);

        int count = 0;
        for (int i = 0; i < recordCnt; i++) {
            Key key = new Key("test", "user", i);
            Record record = client.get(writePolicy, key);
            if (record != null) {
                count++;
                client.delete(writePolicy, key);
            }
        }
        long queryEnd = System.currentTimeMillis();
        System.out.println("Query and Delete by Key total TimeCost:" + (queryEnd - endTime) + "ms, totalCnt:" + count);
    }
}
