package com.zetyun.main.process;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.zetyun.common.RequestBody;

public class SimpleUserProcessor extends SyncUserProcessor<RequestBody> {

    @Override
    public Object handleRequest(BizContext bizCtx, RequestBody request) throws Exception {
        System.out.println("=======>Request received:" + request + ", timeout:" + bizCtx.getClientTimeout()
                + ", arriveTimestamp:" + bizCtx.getArriveTimestamp());
        request.setMsg("[Server response]:" + request.getId());
        return request;
    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }
}
