package com.zetyun.example.thrift;

import com.zetyun.example.thrift.AddService;
import org.apache.thrift.TException;

public class HelloServiceImpl implements AddService.Iface {
    @Override
    public int add(int param1, int param2) throws TException {
        return (param1 + param2);
    }
}
