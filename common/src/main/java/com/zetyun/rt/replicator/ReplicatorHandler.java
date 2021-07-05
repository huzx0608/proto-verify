package com.zetyun.rt.replicator;

import org.apache.thrift.TException;
import org.rocksdb.RocksDBException;
import org.rocksdb.TransactionLogIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReplicatorHandler implements com.zetyun.rt.replicator.Replicator.Iface {
    final static Logger logger = LoggerFactory.getLogger(ReplicatorHandler.class);

    @Override
    public com.zetyun.rt.replicator.ReplicateRsp replicate(com.zetyun.rt.replicator.ReplicateReq request) throws TException {
        RocksdbReplicator replicator = RocksdbReplicator.getInstance();
        String dbName = request.getDbName();
        Long   requestSeqNo = request.getSeqNo();
        ReplicatedDB replicatedDB = replicator.getReplicateDB(dbName);
        com.zetyun.rt.replicator.ReplicateRsp response = new com.zetyun.rt.replicator.ReplicateRsp();
        List<com.zetyun.rt.replicator.Update> updatesList = new ArrayList<>();

        int i = 0;
        int localRecordCnt = 0;
        int localRecordSize = 0;

        Long realSeqNo = replicatedDB.getDbInstance().getLatestSequenceNumber();

        try {
            int retryCnt = 100;
            while (--retryCnt > 0 && requestSeqNo >= realSeqNo) {
                Thread.sleep(100);
                realSeqNo = replicatedDB.getDbInstance().getLatestSequenceNumber();
                System.out.println("No new data. Request SeqNo:" + requestSeqNo + ", RealSeqNo:" + realSeqNo);
            }

            if (retryCnt <= 0 && realSeqNo >= realSeqNo) {
                response.setUpdates(updatesList);
                return response;
            }

            TransactionLogIterator iter = replicatedDB.getDbInstance().getUpdatesSince(requestSeqNo);
            for (i = 0;
                 (i < request.getMaxUpdates()) && (iter != null) && (iter.isValid());
                 i++, iter.next()) {
               TransactionLogIterator.BatchResult result = iter.getBatch();

               localRecordCnt += result.writeBatch().getWriteBatch().count();
               localRecordSize += result.writeBatch().getDataSize();

               com.zetyun.rt.replicator.Update localUpdate = new com.zetyun.rt.replicator.Update();
               localUpdate.setRawData(result.writeBatch().data());
               // todo add log extract to get timestamp
               localUpdate.setTimestamp(System.currentTimeMillis());
               updatesList.add(localUpdate);
            }
            response.setUpdates(updatesList);
        } catch (RocksDBException exp) {
            exp.printStackTrace();
            logger.error("Catch rocksdb operation exception:" + exp.toString(), exp);
        } catch (InterruptedException exp) {
            exp.printStackTrace();
            logger.error("Catch Interrupt exception:" + exp.toString(), exp);
        }
        logger.info("Replicator Loop Index:{}, RecordSize:{}, totalRecordSize:{}",
                i, localRecordCnt, localRecordSize);
        return response;
    }
}
