package com.code47.balking;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread(data, "Changer=>").start();
        new SaverThread("Saver=>", data).start();
    }
}
