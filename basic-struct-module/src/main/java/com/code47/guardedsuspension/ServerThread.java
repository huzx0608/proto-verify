package com.code47.guardedsuspension;

import java.util.Random;

public class ServerThread extends Thread {
    private final RequestQueue requestQueue;
    private final Random random;

    public ServerThread(RequestQueue queue, String name, int seed) {
        super(name);
        this.requestQueue = queue;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        while (true) {
            Request request = requestQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles " + request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
