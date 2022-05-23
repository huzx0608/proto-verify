package com.code;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.WritePolicy;

public class AerospikeWithObject {

    public static void main(String[] args) throws Exception{
        AerospikeClient client = new AerospikeClient("172.20.3.28", 3000);
        WritePolicy writePolicy = new WritePolicy();
        writePolicy.setTimeout(1000);

        String namespaceName = "test";
        String setName = "huzx";

        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null && client.isConnected()) {
                client.close();
            }
        }

    }
}
