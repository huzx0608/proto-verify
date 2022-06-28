package com.coding.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
