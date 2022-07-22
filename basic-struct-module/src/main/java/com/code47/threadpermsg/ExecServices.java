package com.code47.threadpermsg;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecServices {

    public static void main(String[] args) {
        // case1.
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("case1=>Hello");
            }
        });

        // case2.
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "MyThread");
            }
        };

        threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("case2=>Hello");
            }
        }).start();

        // case3.
        ThreadFactory threadFactory01 = Executors.defaultThreadFactory();
        threadFactory01.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("case3=>Hello");
            }
        }).start();

        // case4.
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable runnable) {
                new Thread(runnable).start();
            }
        };

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("case4=>Hello");
            }
        });

        service.shutdown();

    }
}
