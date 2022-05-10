package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class BeanWithCreator {
    private int id;
    private String name;

    @JsonCreator
    public BeanWithCreator(
           @JsonProperty("id") int id,
           @JsonProperty("TheName") String name) {
        this.id = id;
        this.name = name;
    }

}
