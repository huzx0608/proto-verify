package com.zetyun.main.client;

import com.alipay.remoting.ProtocolManager;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.protocol.RpcProtocolV2;
import com.zetyun.common.RequestBody;

import java.util.stream.IntStream;

public class ClientAsyncMain {

    public static RpcClient client;
    public static String    address = "127.0.0.1:9900";

    public static void main(String[] args) {
        ProtocolManager.unRegisterProtocol(RpcProtocolV2.PROTOCOL_CODE);
        client = new RpcClient();
        client.init();

        IntStream.range(1, 100).forEach(x -> {
            RequestBody req = new RequestBody(x, "hello => " + x);
            try {
                RequestBody response = (RequestBody) client.invokeSync(address, req, 3000);
                System.out.println("Huzx =====> response is :" + response.toString());
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
