package com.github.ynfeng.customizeform.publish.http.extractor;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class DepartmentSelectDataExtractor extends AbstractDataExtractor {

    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem) {
        DepartmentSelect departmentSelect = (DepartmentSelect) formItem;
        FormDefinitionItemDataRepresent result = getDataPresent(formId, formItem);

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
