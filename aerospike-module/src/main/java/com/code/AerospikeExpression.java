package com.code;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AerospikeExpression {
    public static final String g_namespace = "test";
    public static final String g_setName = "expression";
    public static final int LIST_RANGE = 2000;
    public static long g_writeCnt = 4000000;

    public static void main(String[] args) throws Exception {
        AerospikeClient client = new AerospikeClient("172.20.3.28", 3000);

        boolean needWrite = true;

        if (args.length > 0) {
            needWrite = (args[0] != null && args[0].toString().toLowerCase().equals("true")) ? true : false;
            g_writeCnt = (args[1] != null) ? Long.valueOf(args[1]) : 4000000L;
        }

        if (needWrite) {
            Random random = new Random(1);
            WritePolicy writePolicy = new WritePolicy();
            writePolicy.sendKey = true;
            for (int i = 0; i <= g_writeCnt; i++) {
                Key key = new Key(g_namespace, g_setName, "id-" + i);
                Bin bin1 = new Bin(new String("bin1"), i);
                ArrayList<Integer> intList = new ArrayList<>();
                for (int j = 0; j < 20; j++) {
                    intList.add(random.nextInt(LIST_RANGE));
                }
                Bin bin2 = new Bin("bin2", intList);
                Bin bin3 = new Bin("bin3", (i + 1) * 100);
                Bin bin4 = new Bin("bin4", Math.sqrt(i + 1) * 100.0);
                Bin bin5 = new Bin("bin5", Math.sqrt(i + 1) * 100.0);
                Bin bin6 = new Bin("bin6", Math.sqrt(i + 1) * 100.0);
                Bin bin7 = new Bin("bin7", Math.sqrt(i + 1) * 100.0);
                Bin bin8 = new Bin("bin8", Math.sqrt(i + 1) * 100.0);
                Bin bin9 = new Bin("bin9", i / 100);
                client.put(writePolicy, key, bin1, bin2, bin3, bin4, bin5, bin6, bin7, bin8, bin9);
            }
        }

        long loopCnt = 0L;
        long rangeQueryTotalTime = 0L;
        long pointQueryTotalTime = 0L;

        while (true) {
            Statement statement = new Statement();
            statement.setNamespace(g_namespace);
            statement.setSetName(g_setName);

            for (int j = 0; j < g_writeCnt; j++) {
                long rangeQueryStartTime = System.currentTimeMillis();
                loopCnt++;
                QueryPolicy queryPolicy = new QueryPolicy(client.queryPolicyDefault);
                queryPolicy.filterExp = Exp.build(Exp.eq(
                        Exp.intBin("bin9"),
                        Exp.val(j / 100)
                ));

                RecordSet rs = client.query(queryPolicy, statement);
                int resultSize = 0;
                long rangeQuerySumValue = 0L;
                List<Key> keyList = new ArrayList<>();
                while (rs.next()) {
                    Key key = rs.getKey();
                    keyList.add(key);
                    List<Long> bin3Value = (List<Long>) rs.getRecord().getList("bin2");
                    rangeQuerySumValue += bin3Value.stream().reduce(0L, Long::sum);
                    resultSize++;
                }
                rs.close();
                long rangeQueryEndTime = System.currentTimeMillis();
                rangeQueryTotalTime += (rangeQueryEndTime - rangeQueryStartTime);

                long pointStartTime = System.currentTimeMillis();
                Long pointQuerySumValue = 0L;
                for (Key tempKey: keyList) {
                    Record record = client.get(client.queryPolicyDefault, tempKey);
                    List<Long> bin3Value = (List<Long>) record.getList("bin2");
                    pointQuerySumValue += bin3Value.stream().reduce(0L, Long::sum);
                }
                long pointEndTime = System.currentTimeMillis();
                pointQueryTotalTime += (pointEndTime - pointStartTime);

                System.out.println("Total time cost:" + rangeQueryTotalTime
                        + ", totalLoopCnt:" + loopCnt
                        + ", resultCnt:" + resultSize
                        + ", QueryAvgTime:[Range:" + rangeQueryTotalTime * 1.0 / loopCnt
                        + ", Point:" + pointQueryTotalTime * 1.0 / loopCnt
                        + "], SumValue:[Range:" + rangeQuerySumValue + ", Point:" + pointQuerySumValue + "]"
                );
            }
        }
    }
}
