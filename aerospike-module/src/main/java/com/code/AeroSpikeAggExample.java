package com.code;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Language;
import com.aerospike.client.Value;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.ResultSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.IndexTask;
import com.aerospike.client.task.RegisterTask;

public class AeroSpikeAggExample {

    public static final String g_default_namespace = "test";
    public static final String g_default_setName = "aggeragation";

    public static void main(String[] args) {
        String indexName = "aggindex";
        String keyPrefix = "aggkey";
        String binName = "aggbin";

        // 1. register task
        AerospikeClient client = new AerospikeClient("172.20.3.28", 3000);
        WritePolicy writePolicy = new WritePolicy();
        RegisterTask task = client.register(writePolicy, "udf/sum_example.lua", "sum_example.lua", Language.LUA);
        task.waitTillComplete();

        Policy defaultPolicy = new Policy();
        defaultPolicy.socketTimeout = 0;
        /*
        IndexTask indexTask = client.createIndex(
                defaultPolicy,
                g_default_namespace,
                g_default_setName,
                indexName,
                binName,
                IndexType.NUMERIC);
        indexTask.waitTillComplete();
        */


        // 2. write record to the store
        for (int i = 0; i < 100; i++) {
            Key key = new Key(g_default_namespace, g_default_setName, keyPrefix + i);
            Bin bin = new Bin(binName, i);
            client.put(writePolicy, key, bin);
        }

        // 3. run query from the store
        long startTime = System.currentTimeMillis();
        int begin = 4;
        int end = 7;
        Statement statement = new Statement();
        statement.setNamespace(g_default_namespace);
        statement.setSetName(g_default_setName);
        statement.setFilter(Filter.range(binName, begin, end));
        statement.setAggregateFunction("sum_example", "sum_single_bin", Value.get(binName));

        ResultSet resultSet = client.queryAggregate(null, statement);
        while (resultSet.next()) {
            Object result = resultSet.getObject();
            long sum;

            if (result instanceof Long) {
                sum = (Long)(resultSet.getObject());
                System.out.println("===> Sum is:" + sum);
            } else {
                System.out.println("===> Return value is not Long");
                continue;
            }
        }
        resultSet.close();
        long endTime = System.currentTimeMillis();
        System.out.println("cost: " + (endTime - startTime));
    }
}
