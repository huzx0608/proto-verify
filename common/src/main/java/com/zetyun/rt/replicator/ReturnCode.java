package com.zetyun.rt.replicator;

public enum ReturnCode {
    OK(0, "OK"),
    DB_NOT_FOUND(1, "DB not found"),
    DB_PRE_EXIST(2, "DB Already Exist"),
    WRITE_TO_SLAVE(3, "Operation on Slave"),
    WRITE_ERROR(4, "Write Error"),
    WAIT_SLAVE_TIMEOUT(5, "Write Slave Timeout");

    private int retCode;
    private String errMsg;
    private ReturnCode(int retCode, String errMsg) {
        this.retCode = retCode;
        this.errMsg = errMsg;
    }
}
