package com.github.ynfeng.customizeform.publish.http.datalinker;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemRepresent;
import org.springframework.hateoas.Link;

public class DefaultDataLinker implements DataLinker {

    @Override
    public void makeLinks(String formId, Component formItem, FormDefinitionItemRepresent formItemRepresent) {
        Link dataLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItemData(formId, formItem.name(), null))
            .withRel("data");
        formItemRepresent.add(dataLink);
    }
}
