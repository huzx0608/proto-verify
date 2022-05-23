package com.code;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.policy.WritePolicy;

import java.util.Arrays;
import java.util.List;

public class AerospikeWithList {

    public static void main(String[] args) throws Exception{
        AerospikeClient client = new AerospikeClient("172.20.3.28", 3000);
        WritePolicy writePolicy = new WritePolicy();
        writePolicy.setTimeout(1000);

        String namespace = "test";
        String setName = "huzx";
        int recordCnt = 10;

        List<String> columns = Arrays.asList("name", "age", "address", "minutes");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < recordCnt; i++) {
            Key key = new Key(namespace, setName, i);
            Bin bin1 = new Bin(columns.get(0), "huzx" + i);
            Bin bin2 = new Bin(columns.get(1), 100 + i);
            Bin bin3 = new Bin(columns.get(2),  Arrays.asList("shanghai", "beijing", "guangzhou"));
            Bin bin4 = new Bin(columns.get(3),  Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100));
            client.put(writePolicy, key, bin1, bin2, bin3, bin4);
        }

        for (int j = 0; j < 1; j++) {
            Key key = new Key(namespace, setName, j);
            Record record = client.get(null, key);
            Record record1 = client.operate(null, key, ListOperation.get(columns.get(2), -1));
            Record record2 = client.operate(null, key,
                    ListOperation.getByValueRange(columns.get(3), Value.get(51), Value.get(90), ListReturnType.VALUE));

            System.out.println("================Before Operation===========");
            System.out.println("M1=> getList Value is=>" + record.getValue(columns.get(2)));
            System.out.println("M2=> getList Value is=>" + record1.getValue(columns.get(2)));
            System.out.println("M3=> getList Value is=>" + record2.getValue(columns.get(3)));

            System.out.println("================After Operation===========");
            System.out.println("Origin list object is=>" + record.getValue(columns.get(2)));
            int secondPosition = 1;
            int beforeLastPostition = -1;
            String newAddress = "JiNan";

            Record modifyRecord = client.operate(null, key,
                    ListOperation.insert(columns.get(2), beforeLastPostition, Value.get(newAddress)),
                    ListOperation.insert(columns.get(2), secondPosition, Value.get(newAddress))
            );

            Record finalRecord = client.get(null, key);
            System.out.println("After Insert Record Result=> " + finalRecord.getValue(columns.get(2)));

            int firstPosition = 0;
            client.operate(null, key, ListOperation.remove(columns.get(2), firstPosition));
            finalRecord = client.get(null, key);
            System.out.println("After remove record result=> " + finalRecord.getValue(columns.get(2)));

        }
    }
}
