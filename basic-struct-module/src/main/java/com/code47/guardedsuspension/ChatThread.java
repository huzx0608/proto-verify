package com.code47.guardedsuspension;

public class ChatThread extends Thread {
    private final RequestQueue inQueue;
    private final RequestQueue outQueue;
    private final String name;

    public ChatThread(RequestQueue inQueue, RequestQueue outQueue, String name) {
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":Begin");
        for (int i = 0; i < 10; i++) {
            Request inRequest = inQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " gets " + inRequest);

            Request outRequest = new Request(inRequest.getName() + "!");
            outQueue.putRequest(outRequest);
            System.out.println(Thread.currentThread().getName() + " puts " + outRequest);
        }
        System.out.println(Thread.currentThread().getName() + ":End");

    }
}
