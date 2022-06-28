package com.coding;

import com.coding.model.Person;
import org.apache.juneau.json.JsonParser;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.json.SimpleJsonSerializer;
import org.apache.juneau.parser.ParseException;
import org.apache.juneau.parser.ReaderParser;
import org.apache.juneau.serializer.SerializeException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarshallMain {

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("abc");
        person.setAge(20);
        // person.setBirthDay(Date.valueOf("2020-10-10"));
        // person.setBirthTime(Time.valueOf("10:10:10"));
        // person.setTimestamp(Timestamp.valueOf("2020-10-10 10:10:10.111"));
        person.setAddress(Arrays.asList("beijing", "shanghai"));
        person.setValues(Arrays.asList(1.1, 2, 3.2));

        String resultInJsonFormat = "";
        try {
            String output1 = SimpleJsonSerializer.DEFAULT.serialize(person);
            System.out.println("serializer in json format:" + output1);
            String output2 = JsonSerializer.DEFAULT.serialize(person);
            System.out.println("serializer in json format:" + output2);
            resultInJsonFormat = output2;
        } catch (SerializeException e) {
            throw new RuntimeException(e);
        }

        ReaderParser parser = JsonParser.DEFAULT;
        try {
            Person newPerson = parser.parse(resultInJsonFormat, Person.class);
            System.out.println("New Person is:" + newPerson);
            String json = "{a:[ " +
                    "           {name:'John Smith',age:21, address:['beijing','shanghai'], values:[1.1,2,3.2]}, " +
                    "           {name:'Joe Smith', age:42, address:['beijing','shanghai'], values:[1.1,2,3.2]}" +
                    "         ]" +
                    "}";
            Map<String, List<Person>> personListMap = parser.parse(
                    json,
                    HashMap.class,
                    String.class,
                    LinkedList.class,
                    Person.class
            );

            for (Map.Entry<String, List<Person>> entry: personListMap.entrySet()) {
                System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
