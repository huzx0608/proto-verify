package com.code47.threadpermsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executeService = Executors.newCachedThreadPool();
        System.out.println("===========Main Start===========");
        try {
            Host host = new Host(executeService);
            host.request(10, 'A');
            host.request(20, 'B');
            host.request(30, 'C');
        } finally {
            executeService.shutdown();
        }
        System.out.println("===========Main End===========");
    }
}
