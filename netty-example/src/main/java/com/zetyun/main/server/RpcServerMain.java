package com.zetyun.main.server;

import com.alipay.remoting.ProtocolManager;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.RpcProtocolV2;
import com.zetyun.main.process.SimpleUserProcessor;

public class RpcServerMain {

    public static int serverPort = 9900;
    public static RpcServer server;

    public static void main(String[] args) {
        ProtocolManager.unRegisterProtocol(RpcProtocolV2.PROTOCOL_CODE);
        server = new RpcServer("127.0.0.1", serverPort);
        server.registerUserProcessor(new SimpleUserProcessor());
        server.start();
        System.out.println("Server Start on Port:" + serverPort + ",server:" + server.toString());
    }
}
