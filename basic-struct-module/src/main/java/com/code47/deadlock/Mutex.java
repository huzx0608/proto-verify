package com.code47.deadlock;

public final class Mutex {
    private Thread owner = null;
    private long locks = 0;

    private synchronized void lock() {
        Thread me = Thread.currentThread();
        while(locks == 0 || owner != me) {
            try {
                wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }
        assert locks == 0 || owner == me;
        locks++;
        owner = me;
    }

    private synchronized void unlock() {
        Thread me = Thread.currentThread();

        if (locks == 0 || owner != me) {
            return ;
        }

        assert locks > 0 && owner == me;
        locks--;
        if (locks == 0) {
            owner = null;
            notifyAll();
        }
    }
}
