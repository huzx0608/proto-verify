package com.code47.navigable;

import java.util.List;
import java.util.NavigableMap;
import java.util.stream.Collectors;

public class NavigableMainWithBasicKeyOnTreeMap {

    public static void main(String[] args) {
        NavigableMap<String, Integer> navigableMap = new java.util.TreeMap<>();
        navigableMap.put("A", 1);
        navigableMap.put("H", 2);
        navigableMap.put("O", 3);
        navigableMap.put("X", 4);

        System.out.println("All Map:" + navigableMap.descendingKeySet());
        System.out.println("Floor Entry:" + navigableMap.floorKey("B"));
        System.out.println("Ceiling Entry:" + navigableMap.ceilingKey("B"));

        System.out.println("Floor Entry:" + navigableMap.floorKey("x"));
        System.out.println("Ceiling Entry:" + navigableMap.ceilingKey("x"));

        for (String key : navigableMap.keySet()) {
            System.out.println("Key:" + key + " Value:" + navigableMap.get(key));
        }

        List<String> keyList = navigableMap.keySet().stream().collect(Collectors.toList());
        System.out.println("Key List:" + keyList);
    }
}
