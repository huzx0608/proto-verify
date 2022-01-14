package com.zetyun.eventprocessor;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.NamedThreadFactory;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.zetyun.common.RequestBody;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleServerUserProcessor extends SyncUserProcessor<RequestBody> {

    private ThreadPoolExecutor executor;

    public SimpleServerUserProcessor() {
        this.executor = new ThreadPoolExecutor(
                4,
                8,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                new NamedThreadFactory("Request-process-pool"));
    }

    @Override
    public Object handleRequest(BizContext bizContext, RequestBody request) throws Exception {
        System.out.println("Huzx============> Request received:" + request + ", timeout:" + bizContext.getClientTimeout()
                + ", arriveTimestamp:" + bizContext.getArriveTimestamp());
        String response = "Index:" + request.getId() + ", Response[" + RequestBody.DEFAULT_SERVER_RETURN_STR + "]";
        return response;
    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }

    @Override
    public ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
