package com.github.ynfeng.customizeform.domain.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "code")
@ToString(of = {"name", "code"})
public class Province {
    private final String name;
    private final String code;

    public Province(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
