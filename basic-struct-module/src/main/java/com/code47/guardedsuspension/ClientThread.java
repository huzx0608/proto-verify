package com.code47.guardedsuspension;

import java.util.Random;

public class ClientThread extends Thread {

    private final RequestQueue requestQueue;
    private final Random   random;
    public ClientThread(RequestQueue queue, String name, int seed) {
        super(name);
        this.requestQueue = queue;
        this.random = new Random(seed);
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            Request request = new Request("No:" + i);
            System.out.println(Thread.currentThread().getName() + " put request: " + request);
            requestQueue.putRequest(request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
