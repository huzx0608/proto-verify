package com.zetyun.main.client;

import com.alipay.remoting.ProtocolManager;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcResponseFuture;
import com.alipay.remoting.rpc.protocol.RpcProtocolV2;
import com.zetyun.common.RequestBody;

import java.util.stream.IntStream;

public class ClientFutureMain {

    public static String serverAddr = "127.0.0.1:9900";
    public static RpcClient client;

    public static void main(String[] args) {
        ProtocolManager.unRegisterProtocol(RpcProtocolV2.PROTOCOL_CODE);

        client = new RpcClient();
        client.init();

        IntStream.range(1, 100).forEach(x -> {
            RequestBody request = new RequestBody(x, "Hello boy:" + x);
            try {
                RpcResponseFuture responseFuture = client.invokeWithFuture(serverAddr, request, 3000);
                RequestBody response = (RequestBody) responseFuture.get();
                System.out.println("Huzx => client receive response:" + response.toString());
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
