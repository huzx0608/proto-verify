package com.coding.module;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
public class Person {
    private String name;
    private int age;
    /*
    private Date birthDay;
    private Time birthTime;
    private Timestamp timestamp;
    */
    private List<String> address;
    Object values;
}
