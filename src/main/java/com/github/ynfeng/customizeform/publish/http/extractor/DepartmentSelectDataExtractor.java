package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class DepartmentSelectDataExtractor extends AbstractDataExtractor {

    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem, Map<String, String> params) {
        DepartmentSelect departmentSelect = (DepartmentSelect) formItem;

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), null))
            .withSelfRel();
        FormDefinitionItemDataRepresent result = createDataRepresent(formId, selfLink);

        List<Department> departments = departmentSelect.getDepartmentOptions();

        departments.forEach(department -> {
            Map<String, String> data = Maps.newHashMap();
            data.put("departmentId", department.getDeptId());
            data.put("departmentName", department.getName());

            result.appendData(data);
        });

        return result;
    }
}
