package com.zetyun.rt.rocksdb;

import org.rocksdb.WriteBatch;

public class CloudWalMeta {
    private int operation;
    private long sequenceNo;
    private WriteBatch batchRecord;

    public CloudWalMeta(int operation, long sequenceNo, WriteBatch batchRecord) {
        this.operation = operation;
        this.sequenceNo = sequenceNo;
        this.batchRecord = batchRecord;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public long getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(long sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public WriteBatch getBatchRecord() {
        return batchRecord;
    }

    public void setBatchRecord(WriteBatch batchRecord) {
        this.batchRecord = batchRecord;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("operation=" + operation + ",");
        if (batchRecord != null && batchRecord.count() != 0) {
            builder.append("SequenceNo=" + sequenceNo + ",");
            builder.append("BatchCnt=" + batchRecord.count() + ",");
            builder.append("batchSize=" + batchRecord.getDataSize());
        }
        return builder.toString();
    }
}
