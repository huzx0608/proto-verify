package com.code47.readwrite;


public class ReaderThread extends Thread {
    private final NData buffer;

    public ReaderThread(NData buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] result = buffer.read();
                System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(result));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
