package com.github.ynfeng.customizeform.publish.http;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.extractor.FormDefinitionItemDataExtractor;
import com.github.ynfeng.customizeform.publish.http.extractor.FormDefinitionItemDataExtractorFactory;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class FormDefinitionItemDataSourceRepresent extends RepresentationModel<FormDefinitionItemDataSourceRepresent> {
    private CollectionModel<? extends DataRepresent> datasource = CollectionModel.empty();

    public static FormDefinitionItemDataSourceRepresent fromDomain(String formId, Component formItem, Map<String, String> params) {
        FormDefinitionItemDataExtractorFactory extractorFactory = new FormDefinitionItemDataExtractorFactory();
        FormDefinitionItemDataExtractor extractor = extractorFactory.get(formItem);
        return extractor.extract(formId, formItem, params);
    }

    public void appendData(List<? extends DataRepresent> dataList) {
        datasource = CollectionModel.of(dataList);
    }
}
