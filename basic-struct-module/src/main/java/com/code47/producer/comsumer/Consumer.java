package com.code47.producer.comsumer;

import java.util.Random;

public class Consumer extends Thread {
    private final String name;
    private final Table table;
    private final Random random;

    public Consumer(String name, Table table, int seed) {
        super(name);
        this.name = name;
        this.table = table;
        random = new Random(seed);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cake = table.take();
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
