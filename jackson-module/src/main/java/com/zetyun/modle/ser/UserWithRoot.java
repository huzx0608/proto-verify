package com.zetyun.modle.ser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;

@JsonRootName(value = "user")
@AllArgsConstructor
public class UserWithRoot {
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
}
