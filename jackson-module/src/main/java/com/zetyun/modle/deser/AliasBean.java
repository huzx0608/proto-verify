package com.zetyun.modle.deser;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class AliasBean {
    @JsonAlias({ "f_name", "fName" })
    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;
}
