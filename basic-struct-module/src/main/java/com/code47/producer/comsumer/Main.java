package com.code47.producer.comsumer;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);
        Producer producer1 = new Producer("Producer-1", table, 31415);
        Producer producer2 = new Producer("Producer-2", table, 51415);
        Producer producer3 = new Producer("Producer-3", table, 41415);
        Consumer consumer1 = new Consumer("Consumer-1", table, 32384);
        Consumer consumer2 = new Consumer("Consumer-2", table, 52384);
        Consumer consumer3 = new Consumer("Consumer-3", table, 42384);
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
