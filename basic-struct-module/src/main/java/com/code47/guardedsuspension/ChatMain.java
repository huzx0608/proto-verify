package com.code47.guardedsuspension;

public class ChatMain {
    public static void main(String[] args) {
        final RequestQueue queue1 = new RequestQueue();
        final RequestQueue queue2 = new RequestQueue();
        queue1.putRequest(new Request("Hello"));
        new ChatThread(queue1, queue2, "Alice=>").start();
        new ChatThread(queue2, queue1, "Huzx=>").start();
    }
}
