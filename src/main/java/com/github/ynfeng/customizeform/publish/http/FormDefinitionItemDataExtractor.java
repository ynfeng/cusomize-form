package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.Component;

public interface FormDefinitionItemDataExtractor {
    FormDefinitionItemDataRepresent extract(String fromId, Component formItem);
}
