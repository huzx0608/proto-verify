package com.zetyun.rt.utils;

import lombok.Getter;

@Getter
public class AddressUtils {
    private String address;
    private int    port;

    public AddressUtils(String addressStr) {
        address = addressStr.split(":")[0];
        port = Integer.valueOf(addressStr.split(":")[1]);
    }
}
