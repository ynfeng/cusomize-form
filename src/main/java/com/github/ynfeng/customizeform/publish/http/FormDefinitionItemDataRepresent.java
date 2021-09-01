package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.extractor.FormDefinitionItemDataExtractor;
import com.github.ynfeng.customizeform.publish.http.extractor.FormDefinitionItemDataExtractorFactory;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class FormDefinitionItemDataRepresent extends RepresentationModel<FormDefinitionItemDataRepresent> {
    private final List<Object> data = Lists.newArrayList();

    public static FormDefinitionItemDataRepresent fromDomain(String formId, Component formItem, Map<String, String> params) {
        FormDefinitionItemDataExtractorFactory extractorFactory = new FormDefinitionItemDataExtractorFactory();
        FormDefinitionItemDataExtractor extractor = extractorFactory.get(formItem);
        return extractor.extract(formId, formItem, params);
    }

    public void appendData(Object data) {
        this.data.add(data);
    }
}
