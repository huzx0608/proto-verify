package com.zetyun.bolt;

import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcResponseFuture;
import com.zetyun.common.BoltServer;
import com.zetyun.common.RequestBody;
import com.zetyun.eventprocessor.SimpleServerUserProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class BoltMainClazz {

    public static void main(String[] args) {
        int port = 9999;
        String addr   = "127.0.0.1:" + String.valueOf(port);
        BoltServer server = new BoltServer(port, true);
        server.start();

        SimpleServerUserProcessor serverUserProcessor  = new SimpleServerUserProcessor();
        server.registerUserProcessor(serverUserProcessor);

        RpcClient client = new RpcClient();
        client.init();


        for (int i = 0; i < 100; i++) {
            final List<String> rets = new ArrayList<String>(1);
            CountDownLatch count = new CountDownLatch(1);
            RequestBody req = new RequestBody(i, "hello world");
            try {
                 String responseSync = (String) client.invokeSync(addr, req, 3000);
                 System.out.println("Huzx===> Receive Sync Response:" + responseSync);

                 RpcResponseFuture resFuture = client.invokeWithFuture(addr, req, 3000);
                 String resWithFuture = (String) resFuture.get();
                 System.out.println("Huzx==> Receive Future Response:" + resWithFuture);

                client.invokeWithCallback(addr, req, new InvokeCallback() {
                    Executor executor = Executors.newCachedThreadPool();
                    @Override
                    public void onResponse(Object result) {
                        System.out.println("Xing===> Receive Callback Response[" + result + "]");
                        rets.add((String)result);
                        count.countDown();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        count.countDown();
                        throwable.printStackTrace();
                    }

                    @Override
                    public Executor getExecutor() {
                        return executor;
                    }
                }, 1000);
            } catch (Exception ex) {
                count.countDown();
                ex.printStackTrace();
            }
            try {
                count.await();
                System.out.println("HHHHHH=> Receive callback[" + rets.get(0));
                rets.clear();
            } catch (Exception ex01) {
                ex01.printStackTrace();
            }
        }
        client.shutdown();
        server.stop();
    }
}
