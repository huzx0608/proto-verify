package com.zetyun.modle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RawBean {
    @JsonProperty("id")
    private String name;

    @JsonRawValue
    private String json;
}
