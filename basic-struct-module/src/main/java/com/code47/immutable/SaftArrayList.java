package com.code47.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SaftArrayList {
    public static void main(String[] args) {
        // final List<String> arrayList = Collections.synchronizedList(new ArrayList<>());
        final List<String> arrayList = new CopyOnWriteArrayList<>();
        new WriteThread(arrayList).start();
        new ReadThread(arrayList).start();
    }
}
