package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.publish.http.DepartmentRepresent;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataSourceRepresent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class DepartmentSelectDataExtractor extends AbstractDataExtractor {

    @Override
    public FormDefinitionItemDataSourceRepresent extract(String formId, Component formItem, Map<String, String> params) {
        DepartmentSelect departmentSelect = (DepartmentSelect) formItem;

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), null))
            .withSelfRel();
        FormDefinitionItemDataSourceRepresent result = createDataRepresent(formId, selfLink);

        List<Department> departments = departmentSelect.getDepartmentOptions();

        List<DepartmentRepresent> departmentsData = departments.stream()
            .map(DepartmentRepresent::fromDomain)
            .collect(Collectors.toList());

        result.appendData(departmentsData);
        return result;
    }
}
