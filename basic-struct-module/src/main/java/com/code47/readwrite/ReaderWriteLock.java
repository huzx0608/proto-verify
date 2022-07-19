package com.code47.readwrite;

public class ReaderWriteLock {

    private int readingReaders = 0;
    private int waitingWriters = 0;
    private int writingWriters = 0;
    private boolean perferWrite = true;

    public synchronized void writeLock() throws InterruptedException {
        waitingWriters++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                wait();
            }
        } finally {
            waitingWriters--;
        }
        writingWriters++;
    }

    public synchronized void writeUnlock() {
        writingWriters--;
        perferWrite = false;
        notifyAll();;
    }

    public synchronized void readLock() throws InterruptedException {
        while (writingWriters > 0 || (perferWrite && waitingWriters > 0)) {
            wait();
        }
        readingReaders++;
    }

    public synchronized void readUnlock() {
        readingReaders--;
        perferWrite = true;
        notifyAll();;
    }
}
