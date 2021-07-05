package com.zetyun.rt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemOpUtils {

    public static void runCommand(final String runCommand) {
        try {
            Process process = Runtime.getRuntime().exec(runCommand);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        // SystemOpUtils.runCommand("ping www.baidu.com");
        SystemOpUtils.runCommand("mkdir /tmp/huzx-db0");
        SystemOpUtils.runCommand("rm -rf /tmp/huzx-db0");
    }
}
