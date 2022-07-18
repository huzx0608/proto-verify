package com.code47.balking;

import java.io.IOException;

public class SaverThread extends Thread {
    private final Data data;
    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    public void run() {
        try {
            while (true) {
                data.save();
                Thread.sleep(1000);
            }
        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        } catch (InterruptedException interExp) {
            interExp.printStackTrace();
        }
    }
}
