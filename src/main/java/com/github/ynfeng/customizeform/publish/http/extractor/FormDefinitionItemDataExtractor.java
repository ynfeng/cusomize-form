package com.github.ynfeng.customizeform.publish.http.extractor;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataSourceRepresent;
import java.util.Map;

public interface FormDefinitionItemDataExtractor {
    FormDefinitionItemDataSourceRepresent extract(String formId, Component formItem, Map<String, String> params);
}
