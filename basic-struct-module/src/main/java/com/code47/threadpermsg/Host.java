package com.code47.threadpermsg;

import java.util.concurrent.ExecutorService;

public class Host {

    private final ExecutorService service;
    public Host(ExecutorService service) {
        this.service = service;
    }

    public void request(int count, char c) {
        System.out.println("   Request (" + count + "," + c + ") BEGIN");
        Helper helper = new Helper(count, c);
        service.execute(new Runnable() {
            @Override
            public void run() {
                helper.handle();
            }
        });
        System.out.println("   Request (" + count + "," + c + ") END");
    }
}
