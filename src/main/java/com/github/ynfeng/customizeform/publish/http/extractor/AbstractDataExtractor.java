package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public abstract class AbstractDataExtractor implements FormDefinitionItemDataExtractor {

    protected FormDefinitionItemDataRepresent getDataPresent(String formId, Component formItem) {
        FormDefinitionItemDataRepresent dataRepresent = new FormDefinitionItemDataRepresent();

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), ""))
            .withSelfRel();
        dataRepresent.add(selfLink);

        Link formDefLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinition(formId))
            .withRel("form-definition");
        dataRepresent.add(formDefLink);

        Link formItemsLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItems(formId))
            .withRel("form-definition-items");
        dataRepresent.add(formItemsLink);

        return dataRepresent;
    }

}
