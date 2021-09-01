package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class DefaultDataExtractor extends AbstractDataExtractor {
    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem, Map<String, String> params) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), null))
            .withSelfRel();
        return createDataRepresent(formId, selfLink);
    }
}
