package com.zetyun.modle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class EventWithSerializer {
    @JsonProperty
    private String name;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
}
