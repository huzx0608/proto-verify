package com.zetyun.modle;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnumWithValue {
    TYPE1(1, "TYPE A"), TYPE2(2, "TYPE B"), TYPE3(3, "TYPE C");

    private int id;
    private String name;

    private TypeEnumWithValue(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
