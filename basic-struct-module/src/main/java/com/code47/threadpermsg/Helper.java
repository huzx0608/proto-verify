package com.code47.threadpermsg;

public class Helper {
    private final int count;
    private final char c;

    public Helper(int count, char c) {
        this.count = count;
        this.c = c;
    }

    public void handle() {
        System.out.println("       Handle (" + count + "," + c + ") BEGIN");
        for (int i = 0; i < count; i++) {
            System.out.print(c);
        }
        System.out.println("   ");
        System.out.println("       Handle (" + count + "," + c + ") END");
    }
}
