package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;

import java.util.Date;

@ToString
public class EventWithDeSerializer {
    @JsonProperty("name")
    private String name;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date eventDate;
}
