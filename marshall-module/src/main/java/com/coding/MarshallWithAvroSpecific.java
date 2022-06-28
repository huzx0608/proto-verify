package com.coding;


import com.coding.model.UserSpecific;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MarshallWithAvroSpecific {

    public static void main(String[] args) {
        Schema schema = UserSpecific.getClassSchema();
        System.out.println("Huzx=> Schema is:" + schema.toString());

        UserSpecific user1 = UserSpecific.newBuilder()
                .setName("Huzx")
                .setAge(19)
                .setPhoneNumbers(Arrays.asList("11111", "22222"))
                .setCompany("datacanvas")
                .build();
        System.out.println("User1=>" + user1.toString());

        File outFile = new File("user.data.avro");
        DatumWriter<UserSpecific> writer = new SpecificDatumWriter<>(UserSpecific.class);
        try {
            DataFileWriter<UserSpecific> fileWriter = new DataFileWriter<>(writer).create(schema, outFile);
            fileWriter.append(user1);
            for (int i = 0; i < 10; i++) {
                UserSpecific user = UserSpecific.newBuilder()
                        .setName("Huzx" + i)
                        .setAge(i)
                        .setPhoneNumbers(Arrays.asList("11111", "22222"))
                        .setCompany("datacanvas")
                        .build();
                fileWriter.append(user);
            }
            fileWriter.close();

            DatumReader<UserSpecific> datumReader = new SpecificDatumReader<>(UserSpecific.class);
            DataFileReader<UserSpecific> fileReader = new DataFileReader<UserSpecific>(outFile, datumReader);
            while (fileReader.hasNext()) {
                UserSpecific user = fileReader.next();
                System.out.println("User:" + user.toString());
            }
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
