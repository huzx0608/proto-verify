package com.code47.deadlock;

public class SharedResource {
    private final Tool spoon;
    private final Tool fork;

    public SharedResource(Tool spoon, Tool fork) {
        this.spoon = spoon;
        this.fork = fork;
    }

    @Override
    public String toString() {
        return "[" + spoon + ", " + fork + "]";
    }
}
