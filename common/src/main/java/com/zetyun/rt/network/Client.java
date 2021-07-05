package com.zetyun.rt.network;

import com.zetyun.rt.replicator.ReplicateReq;
import com.zetyun.rt.replicator.ReplicateRsp;
import com.zetyun.rt.replicator.Replicator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {
    private String ipAddr;
    private int    port;
    private boolean isInit;
    private TTransport transport;
    private Replicator.Client client;

    public Client(String ipAddr, int port) {
        this.ipAddr = ipAddr;
        this.port  = port;
        isInit = false;
    }

    public void initConnection() throws Exception {
        if (isInit) {
            return;
        }

        try {
            transport = new TSocket("localhost", port);
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new Replicator.Client(protocol);
            transport.open();
            isInit = true;
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public void close() throws Exception {
        if (null != transport) {
            transport.close();
            isInit = false;
        }
    }

    public ReplicateRsp sendFetchRequest(ReplicateReq request) {
        ReplicateRsp response = null;
        try {
            response = client.replicate(request);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return response;
    }
}
