package com.github.ynfeng.customizeform.publish.http.extractor;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;

public interface FormDefinitionItemDataExtractor {
    FormDefinitionItemDataRepresent extract(String formId, Component formItem);
}
