package com.code47.immutable;

import java.util.List;

public class WriteThread extends Thread {

    private final List<String> arrayList;

    public WriteThread(List<String> arrayList) {
        super("WriteThread");
        this.arrayList = arrayList;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            arrayList.add("Value " + i);
            arrayList.remove(0);
        }
    }
}
