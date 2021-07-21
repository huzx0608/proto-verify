package com.zetyun.example.thrift.client;

import com.zetyun.example.thrift.AddService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {
    public static void main(String[] args) {
        int input1 = Integer.valueOf(args[0]);
        int input2 = Integer.valueOf(args[1]);
        TTransport transport = null;
        try {
            transport = new TSocket("172.20.3.18", 50005);
            TProtocol protocol = new TBinaryProtocol(transport);
            AddService.Client client = new AddService.Client(protocol);
            transport.open();
            System.out.println("Client send request...");
            int result = client.add(input1, input2);
            System.out.println("Receive from server:" + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}
