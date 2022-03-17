package com.zetyun.dingo.customer.model;

public enum DataTypeEnum {
    STRING("string", "字符串"),
    TEXT("longtext", "长字符串"),
    CLOB("clob", "clob"),
    BLOB("blob", "blob"),
    CHAR("char", "字符"),
    NUMBER("number", "数值型"),
    DECIMAL("decimal", "浮点数"),
    DATE("date", "日期类型"),
    TIMESTAMP("timestamp", "时间戳类型"),
    INTEGER("integer", "常用的整数");

    private String type;
    private String name;

    DataTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static DataTypeEnum getByCode(String type) {
        for (DataTypeEnum data: DataTypeEnum.values()) {
            if (data.getType().equals(type)) {
                return data;
            }
        }
        return null;
    }
}
