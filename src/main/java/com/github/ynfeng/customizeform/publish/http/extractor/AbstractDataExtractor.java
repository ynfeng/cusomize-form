package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import org.springframework.hateoas.Link;

public abstract class AbstractDataExtractor implements FormDefinitionItemDataExtractor {

    protected FormDefinitionItemDataRepresent createDataRepresent(String formId, Link selfLink) {
        FormDefinitionItemDataRepresent dataRepresent = new FormDefinitionItemDataRepresent();
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
