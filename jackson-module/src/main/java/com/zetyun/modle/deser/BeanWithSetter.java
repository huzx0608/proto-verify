package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.ToString;

@ToString
public class BeanWithSetter {
    @JsonProperty
    private int id;

    private String name;

    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }
}
