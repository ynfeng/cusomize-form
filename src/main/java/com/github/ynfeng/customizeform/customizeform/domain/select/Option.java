package com.github.ynfeng.customizeform.customizeform.domain.select;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(of = {"name", "value"})
@EqualsAndHashCode(of = {"name", "value"})
public class Option<T> {
    private final String name;
    private final T value;

    public Option(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public static Option of(String name, Object value) {
        return new Option(name, value);
    }

    public T getData() {
        return value;
    }
}
