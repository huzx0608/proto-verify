package com.code47.readwrite;


public class ReaderThread extends Thread {
    private final Data buffer;

    public ReaderThread(Data buffer) {
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
