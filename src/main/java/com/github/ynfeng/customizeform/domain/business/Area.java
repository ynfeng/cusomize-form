package com.github.ynfeng.customizeform.domain.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "code")
@ToString(of = {"cityCode", "name", "code"})
public class Area {
    private final String cityCode;
    private final String name;
    private final String code;

    public Area(String cityCode, String name, String code) {
        this.cityCode = cityCode;
        this.name = name;
        this.code = code;
    }
}
