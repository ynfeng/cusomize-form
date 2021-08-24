package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import org.springframework.hateoas.Link;

public class FormDefinitionItemDataExtractorFactory {

    public FormDefinitionItemDataExtractor get(Component formItem) {
        if (formItem instanceof DepartmentSelect) {
            return new DepartmentSelectDataExtractor();
        }

        return (formId, item) -> {
            FormDefinitionItemDataRepresent dataRepresent = new FormDefinitionItemDataRepresent();

            Link selfLink = linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, item.name()))
                .withSelfRel();
            dataRepresent.add(selfLink);

            return dataRepresent;
        };
    }
}
