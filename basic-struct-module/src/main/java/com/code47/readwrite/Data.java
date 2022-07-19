package com.code47.readwrite;

public class Data {
    private final ReaderWriteLock lock = new ReaderWriteLock();
    private final char[] buffer;

    public Data(int size) {
        buffer = new char[size];
        doWrite('*');
    }

    public void write(char c) throws InterruptedException {
        lock.writeLock();
        try {
            doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }

    private char[] doRead() {
        char[] newBuffer = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newBuffer[i] = buffer[i];
        }
        slowly();
        return newBuffer;
    }

    public char[] read() throws InterruptedException {
        lock.readLock();
        try {
            char[] result = doRead();
            return result;
        } finally {
            lock.readUnlock();
        }
    }

    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
