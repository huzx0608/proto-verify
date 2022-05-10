package com.zetyun.modle.ser;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Map;

public class ExtendableBean {
    public String name;

    @Setter
    private Map<String, String> properties;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public void add(String key, String value) {
        properties.put(key, value);
    }

    public ExtendableBean(String name) {
        this.name = name;
        properties = new java.util.HashMap<>();
    }

}
