package com.github.ynfeng.customizeform.publish.http.extractor;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;

public class FormDefinitionItemDataExtractorFactory {

    public FormDefinitionItemDataExtractor get(Component formItem) {
        if (formItem instanceof DepartmentSelect) {
            return new DepartmentSelectDataExtractor();
        }

//        if (formItem instanceof AddressSelect) {
//            return new AddressSelectDataExtractor();
//        }

        return new DefaultDataExtractor();
    }
}
