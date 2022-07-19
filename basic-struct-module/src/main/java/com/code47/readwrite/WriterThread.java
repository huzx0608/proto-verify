package com.code47.readwrite;

import java.util.Random;

public class WriterThread extends Thread {
    private final Data buffer;
    private final String loopStr;
    private static final Random random = new Random();
    private int index;

    public WriterThread(Data buffer, String loopStr) {
        this.buffer = buffer;
        this.loopStr = loopStr;
        index = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char nextChar = nextChar();
                buffer.write(nextChar);
                Thread.sleep(random.nextInt(3000));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private char nextChar() {
        char result = loopStr.charAt(index);
        index = (++index) % loopStr.length();
        return result;
    }
}
