package com.coding;

import com.coding.model.User;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

public class MarshallWithAvroReflect {

    public static void main(String[] args) throws IOException {

        Schema schema = ReflectData.get().getSchema(User.class);
        System.out.println("Huzx=> Schema is:" + schema.toString());

        User user1 = User.builder()
                .name("Huzx")
                .age(19)
                .phoneNumbers(Arrays.asList("11111", "22222"))
                .company("datacanvas")
                .lastUpdateTime(Instant.now())
                .build();
        System.out.println("User1=>" + user1.toString());

        String fileName = "user.data.avro";
        File outFile = new File(fileName);
        DatumWriter<User> writer = new ReflectDatumWriter<>(User.class);
        DataFileWriter<User> fileWriter = new DataFileWriter<>(writer).create(schema, outFile);
        fileWriter.append(user1);

        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .name("Huzx" + i)
                    .age(i)
                    .phoneNumbers(Arrays.asList("11111", "22222"))
                    .company("datacanvas")
                    .lastUpdateTime(Instant.now())
                    .build();
            fileWriter.append(user);
        }
        fileWriter.close();
        System.out.println("All data has been write to file:" + fileName);

        DatumReader<User> datumReader = new ReflectDatumReader<>(User.class);
        DataFileReader<User> fileReader = new DataFileReader<User>(outFile, datumReader);
        while (fileReader.hasNext()) {
            User user = fileReader.next();
            System.out.println("User:" + user.toString());
        }
        fileReader.close();
    }
}
