package com.zetyun.main.client;

import com.alipay.remoting.Connection;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.zetyun.common.RequestBody;

import java.util.stream.IntStream;

public class ClientConnMain {

    public static String serverAddr = "127.0.0.1:9900";
    public static RpcClient client;
    public static Connection connection;

    public static void main(String[] args) {
        client = new RpcClient();
        client.init();
        try {
            connection = client.createStandaloneConnection(serverAddr, 3000);
        } catch (RemotingException e) {
            e.printStackTrace();
        }

        IntStream.range(1, 100).forEach(x -> {
            RequestBody request = new RequestBody(x, "Hello " + x);
            try {
                RequestBody response = (RequestBody) client.invokeSync(connection, request, 3000);
                System.out.println("Client => receive response:" + response);
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
