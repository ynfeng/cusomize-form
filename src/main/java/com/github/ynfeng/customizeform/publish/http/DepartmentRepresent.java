package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.business.Department;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "departmentList", itemRelation = "department")
public class DepartmentRepresent extends DataRepresent {
    private final String departmentId;
    private final String departmentName;

    private DepartmentRepresent(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public static DepartmentRepresent fromDomain(Department department) {
        return new DepartmentRepresent(department.getDeptId(), department.getName());
    }
}
