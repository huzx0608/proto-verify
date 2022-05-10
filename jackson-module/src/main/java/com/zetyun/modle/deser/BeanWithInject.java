package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class BeanWithInject {
    @JacksonInject
    private int id;

    private String name;

    @JsonCreator
    public BeanWithInject(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
