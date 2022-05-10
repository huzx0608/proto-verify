package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class ExtendableBean {
    @JsonProperty
    private String name;

    @JsonProperty
    private Map<String, String> properties;

    public ExtendableBean() {
        properties = new HashMap<>();
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }
}
