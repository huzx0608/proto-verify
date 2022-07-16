package com.code47.immutable;

import java.util.List;

public class ReadThread extends Thread {
    private final List<String> arrayList;

    public ReadThread(List<String> arrayList) {
        super("ReadThread");
        this.arrayList = arrayList;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (arrayList) {
                for (String value : arrayList) {
                    System.out.println(value);
                }
            }
        }
    }
}
