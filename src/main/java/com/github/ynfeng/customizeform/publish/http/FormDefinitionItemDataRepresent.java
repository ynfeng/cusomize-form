package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.Component;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class FormDefinitionItemDataRepresent extends RepresentationModel<FormDefinitionItemDataRepresent> {
    private final List<Object> data = Lists.newArrayList();

    public static FormDefinitionItemDataRepresent fromDomain(String formId, Component formItem) {
        FormDefinitionItemDataExtractorFactory extractorFactory = new FormDefinitionItemDataExtractorFactory();
        FormDefinitionItemDataExtractor extractor = extractorFactory.get(formItem);
        return extractor.extract(formId, formItem);
    }

    public void appendData(Map<String, String> data) {
        this.data.add(data);
    }
}
