package com.code47.deadlock;

public class DeadLockMain {
    public static void main(String[] args) {
        Tool spoon = new Tool("Spoon");
        Tool fork = new Tool("Fork");
        new EaterThread("Alice", spoon, fork).start();
        new EaterThread("Bobby", spoon, fork).start();
    }
}

class  EaterThread extends Thread {
    private String name;
    private final Tool spoon;
    private final Tool fork;

    public EaterThread(String name, Tool spoon, Tool fork) {
        this.name = name;
        this.spoon = spoon;
        this.fork = fork;
    }

    @Override
    public void run() {
        while (true) {
            eat();
        }
    }

    private void eat() {
        synchronized (spoon) {
            System.out.println(name + " takes up " + spoon + ", (left)");
            synchronized (fork) {
                System.out.println(name + " takes up " + fork + ", (right)");
                System.out.println(name + " eats.... yum yum!");
                System.out.println(name + " puts down " + fork + ", (right)");
            }
            System.out.println(name + " puts down " + spoon + ", (left)");
        }
    }
}

class Tool {
    private String name;

    public Tool(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }
}
