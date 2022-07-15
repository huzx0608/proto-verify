package com.code47.singlethreadexecution.impl;

public class SingletonCase1 {
    public static void main(String[] args) {
        Gate gate = new Gate();
        new UserThread(gate, "Alice", "Alaska").start();
        new UserThread(gate, "Bobby", "Brazil").start();
        new UserThread(gate, "Charlie", "Canada").start();
    }
}

class UserThread extends Thread {
    private Gate gate;
    private String name;
    private String address;

    public UserThread(Gate gate, String name, String address) {
        this.gate = gate;
        this.name = name;
        this.address = address;
    }
    @Override
    public void run() {
        System.out.println("++++++++++++++++++++++++++");
        while (true) {
            gate.pass(name, address);
        }
    }
}

class Gate {
    private int count = 0;
    private String name = "Nobody";
    private String address = "Nowhere";

    public synchronized void pass(String name, String address) {
        this.count++;
        this.name = name;
        this.address = address;
        check();
    }

    @Override
    public synchronized String toString() {
        return "No." + count + ": " + name + ", " + address;
    }

    private void check() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("Error: " + name + " and " + address + " are not in the same city.");
        } else {
            System.out.println(this);
        }
    }
}
