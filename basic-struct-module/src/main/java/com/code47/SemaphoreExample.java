package com.code47;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        BoundedResource resource = new BoundedResource(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new UserThread(resource)).start();
        }
    }
}

class BoundedResource {
    private final Semaphore semaphore;
    private final int permits;
    private final Random random = new Random(314519);

    public BoundedResource(int permits) {
        this.permits = permits;
        this.semaphore = new Semaphore(permits);
    }

    public void use() throws InterruptedException {
        semaphore.acquire();
        try {
            doUse();
        } finally {
            semaphore.release();
        }
    }

    private void doUse() throws InterruptedException {
        Log.log("Begin: used=" + (permits - semaphore.availablePermits()));
        Thread.sleep(random.nextInt(1000));
        Log.log("End: used=" + (permits - semaphore.availablePermits()));
    }
}

class Log {
    public static void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}

class UserThread extends Thread {
    private final static Random random = new Random(314519);
    private final BoundedResource resource;

    public UserThread(BoundedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            while (true) {
                resource.use();
                Thread.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
