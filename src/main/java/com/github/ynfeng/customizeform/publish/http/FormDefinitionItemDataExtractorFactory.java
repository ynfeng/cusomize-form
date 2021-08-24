package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;

public class FormDefinitionItemDataExtractorFactory {

    public FormDefinitionItemDataExtractor get(Component formItem) {
        if (formItem instanceof DepartmentSelect) {
            return new DepartmentSelectDataExtractor();
        }
        return null;
    }
}
