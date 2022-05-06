package com.zetyun.modle;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonPropertyOrder({ "name", "id"})
public class MyBean {

    @JsonProperty
    private int id;
    private String name;

    @JsonGetter("name")
    public String getTheName() {
        return name;
    }
}
