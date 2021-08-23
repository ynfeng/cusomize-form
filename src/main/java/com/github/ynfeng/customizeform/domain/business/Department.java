package com.github.ynfeng.customizeform.domain.business;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"deptId", "name"})
@EqualsAndHashCode(of = "deptId")
public class Department {
    private final String deptId;
    private final String name;

    public Department(String deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }
}
