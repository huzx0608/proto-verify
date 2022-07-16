package com.code47.guardedsuspension;


public class Main {
    public static void main(String[] args) {
        final RequestQueue requestQueue = new RequestQueue();
        new ServerThread(requestQueue, "Server=>", 23456).start();
        new ClientThread(requestQueue, "Client=>", 12345).start();
    }
}
