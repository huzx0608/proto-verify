package com.code.navigable;

import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class NavigableMainWithBasicKeyOnSkipList {

    public static void main(String[] args) {
        NavigableMap<String, Integer> navigableMap = new ConcurrentSkipListMap<>(String::compareTo);
        navigableMap.put("A", 1);
        navigableMap.put("H", 2);
        navigableMap.put("O", 3);
        navigableMap.put("X", 4);

        System.out.println("All Map:" + navigableMap.descendingKeySet());
        System.out.println("Floor Entry:" + navigableMap.floorKey("B"));
        System.out.println("Ceiling Entry:" + navigableMap.ceilingKey("B"));

        System.out.println("Floor Entry:" + navigableMap.floorKey("x"));
        System.out.println("Ceiling Entry:" + navigableMap.ceilingKey("x"));
    }
}
