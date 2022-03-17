package com.zetyun.dingo.customer.table;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
public class Table implements Serializable {
    private String schema;
    private String table;
    private List<Field> fields;

    @Data
    public static class Field implements Serializable {
        private String name;
        private String type;
        public Field() {
        }
        public Field(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
}
