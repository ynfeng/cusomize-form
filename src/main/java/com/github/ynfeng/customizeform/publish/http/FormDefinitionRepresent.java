package com.github.ynfeng.customizeform.publish.http;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "form-definitions", itemRelation = "form-definition")
public class FormDefinitionRepresent extends RepresentationModel<FormDefinitionRepresent> {
    private String formId;
    private String name;

    public static FormDefinitionRepresent fromDomain(FormDefinition formDefinition) {
        FormDefinitionRepresent result = new FormDefinitionRepresent();

        result.formId = formDefinition.formId();
        result.name = formDefinition.name();

        Link selfLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinition(formDefinition.formId()))
            .withSelfRel();
        result.add(selfLink);

        Link itemsLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItems(formDefinition.formId()))
            .withRel("form-definition-items");
        result.add(itemsLink);

        return result;
    }
}
