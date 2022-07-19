package com.code47.producer.comsumer;

import java.util.Random;

public class Producer extends Thread {
    private final String name;
    private final Random random;
    private final Table table;
    private static int index;

    public Producer(String name, Table table, int seed) {
        super(name);
        this.name = name;
        this.table = table;
        this.random = new Random(seed);
        index = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cake = "[ Cake No." + nextInt() + " by " + getName() + " ]";
                table.put(cake);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized static int nextInt() {
        return index++;
    }
}
