package com.github.ynfeng.customizeform.domain.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "code")
@ToString(of = {"provinceCode", "name", "code"})
public class City {
    private final String provinceCode;
    private final String name;
    private final String code;

    public City(String provinceCode, String name, String code) {
        this.provinceCode = provinceCode;
        this.name = name;
        this.code = code;
    }
}
