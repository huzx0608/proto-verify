package com.zetyun.rt.network;

import com.zetyun.rt.replicator.Replicator;
import com.zetyun.rt.replicator.ReplicatorHandler;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

public class Server extends Thread {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            TProcessor tprocessor = new Replicator.Processor<>(new ReplicatorHandler());
            TServerSocket serverTransport = new TServerSocket(this.port);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
