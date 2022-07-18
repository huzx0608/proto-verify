package com.code47.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Data {
    private final String name;
    private String content;
    private boolean isChanged;

    public Data(String name, String content) {
        this.name = name;
        this.content = content;
        this.isChanged = true;
    }

    public synchronized void change(String content) {
        this.content = content;
        this.isChanged = true;
    }

    public synchronized void save() throws IOException {
        if (!isChanged) {
            return;
        }

        doSave();
        isChanged = false;
    }

    private void doSave() throws IOException  {
        System.out.println(Thread.currentThread().getName() + " call save content= " + content);
        Writer writer = new FileWriter(name);
        writer.write(content);
        writer.close();
    }

}
