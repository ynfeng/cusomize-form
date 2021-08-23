package com.github.ynfeng.customizeform.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "name")
public class User {
    private final String name;

    public User(String name) {
        this.name = name;
    }
}
