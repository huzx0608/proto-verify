package com.code47.guardedsuspension;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private final Queue<Request> requestQueue = new LinkedList<Request>();

    public synchronized Request getRequest() {
        while (requestQueue.peek() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return requestQueue.remove();
    }

    public synchronized void putRequest(Request request) {
        requestQueue.offer(request);
        notifyAll();
    }

}
