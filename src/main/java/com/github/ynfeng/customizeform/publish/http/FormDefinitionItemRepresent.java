package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.publish.http.datalinker.DataLinker;
import com.github.ynfeng.customizeform.publish.http.datalinker.DataLinkerFactory;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "form-definition-items", itemRelation = "form-definition-item")
public class FormDefinitionItemRepresent extends RepresentationModel<FormDefinitionItemRepresent> {
    private String name;
    private String screenName;
    private String type;

    public static List<FormDefinitionItemRepresent> fromDomain(FormDefinition formDefinition) {
        List<FormDefinitionItemRepresent> result = Lists.newArrayList();

        formDefinition.items().forEach(formItem -> {
            FormDefinitionItemRepresent formItemRepresent = new FormDefinitionItemRepresent();

            formItemRepresent.name = formItem.name();
            formItemRepresent.screenName = formItem.screenName();
            formItemRepresent.type = formItem.getClass().getSimpleName();

            Link selfLink = linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItems(formDefinition.formId()))
                .withSelfRel();
            formItemRepresent.add(selfLink);

            DataLinker dataLinker = new DataLinkerFactory().get(formItem.getClass());
            dataLinker.makeLinks(formDefinition.formId(), formItem, formItemRepresent);

            result.add(formItemRepresent);
        });

        return result;
    }
}
