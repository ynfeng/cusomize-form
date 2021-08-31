package com.github.ynfeng.customizeform.publish.http.extractor;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;

public class DefaultDataExtractor extends AbstractDataExtractor {
    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem) {
        return getDataPresent(formId, formItem);
    }
}
