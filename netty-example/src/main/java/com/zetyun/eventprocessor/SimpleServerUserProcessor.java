package com.zetyun.eventprocessor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.zetyun.common.RequestBody;

public class SimpleServerUserProcessor extends SyncUserProcessor<RequestBody> {


    @Override
    public Object handleRequest(BizContext bizContext, RequestBody request) throws Exception {
        System.out.println("Huzx==> Request received:" + request + ", timeout:" + bizContext.getClientTimeout()
                + ", arriveTimestamp:" + bizContext.getArriveTimestamp());
        String response = "Index:" + request.getId() + ", Response[" + RequestBody.DEFAULT_SERVER_RETURN_STR + "]";
        return response;
    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }
}
