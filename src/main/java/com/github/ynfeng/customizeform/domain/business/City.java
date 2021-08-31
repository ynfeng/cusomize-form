package com.github.ynfeng.customizeform.domain.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "code")
@ToString(of = {"province", "name", "code"})
public class City {
    private final Province province;
    private final String name;
    private final String code;

    public City(Province province, String name, String code) {
        this.province = province;
        this.name = name;
        this.code = code;
    }
}
