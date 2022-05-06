package com.zetyun.dingo.simple;

import org.apache.commons.lang3.StringEscapeUtils;

public class MainFunc {

    public static void main(String[] args) {
        String result = StringEscapeUtils.unescapeJson("\\aaaa");
        System.out.println(result);
    }
}
