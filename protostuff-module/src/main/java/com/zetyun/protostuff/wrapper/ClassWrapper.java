package com.zetyun.protostuff.wrapper;

public class ClassWrapper {
    private Object wrappedValue;

    public ClassWrapper(){
        wrappedValue = null;
    }

    public Object getValue() {
        return wrappedValue;
    }

    public void setValue(Object value) {
        this.wrappedValue = value;
    }
}
