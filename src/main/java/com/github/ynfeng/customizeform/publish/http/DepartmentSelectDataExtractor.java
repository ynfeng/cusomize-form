package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.hateoas.Link;

public class DepartmentSelectDataExtractor implements FormDefinitionItemDataExtractor {

    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem) {
        DepartmentSelect departmentSelect = (DepartmentSelect) formItem;

        FormDefinitionItemDataRepresent result = new FormDefinitionItemDataRepresent();

        Link selfLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItemData(formId, formItem.name()))
            .withSelfRel();
        result.add(selfLink);

        List<Department> depts = departmentSelect.getDepartmentOptions();

        depts.forEach(department -> {
            Map<String, String> data = Maps.newHashMap();
            data.put("departmentId", department.getDeptId());
            data.put("departmentName", department.getName());

            result.appendData(data);
        });

        return result;
    }
}
