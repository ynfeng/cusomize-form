package com.github.ynfeng.customizeform.domain.datasource;

public class Data {
    private final String name;
    private final Object value;

    public Data(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public static Data of(String name, Object value) {
        return new Data(name, value);
    }

    public String name() {
        return name;
    }

    public Object value() {
        return value;
    }
}
