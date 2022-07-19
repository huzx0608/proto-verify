package com.code47.producer.comsumer;

public class Table {
    private final String[] buffer;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    public Table(int size) {
        this.buffer = new String[size];
    }

    public synchronized void put(String cake) throws InterruptedException {
       while (count >= buffer.length ) {
           wait();
       }
       System.out.println(Thread.currentThread().getName() + " puts " + cake);
       buffer[tail] = cake;
       tail = (tail + 1) % buffer.length;
       count++;
       notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
        }

        String result = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " takes " + result);
        return result;
    }

}
