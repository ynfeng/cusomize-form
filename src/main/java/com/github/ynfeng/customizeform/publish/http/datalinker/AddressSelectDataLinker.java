package com.github.ynfeng.customizeform.publish.http.datalinker;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemRepresent;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class AddressSelectDataLinker implements DataLinker {
    @Override
    public void makeLinks(String formId, Component formItem, FormDefinitionItemRepresent formItemRepresent) {
        Map<String, String> params = Maps.newHashMap();
        params.put("type", "province");

        Link provinceDataLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItemData(formId, formItem.name(), params))
            .withRel("province-data");
        formItemRepresent.add(provinceDataLink);

        params = Maps.newHashMap();
        params.put("type", "city");
        Link cityDataLink = createTemplateLink(linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), params)),
            "city-data", "provinceCode");
        formItemRepresent.add(cityDataLink);

        params = Maps.newHashMap();
        params.put("type", "area");
        Link areaDataLink = createTemplateLink(linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), params)),
            "area-data", "areaCode");
        formItemRepresent.add(areaDataLink);
    }

    private static Link createTemplateLink(LinkBuilder linkBuilder, String rel, String... templateVariables) {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(linkBuilder.toUri()).build();
        UriTemplate template = UriTemplate.of(uriComponents.toUriString());

        for (String templateVariable : templateVariables) {
            template = template.with(templateVariable, TemplateVariable.VariableType.REQUEST_PARAM);
        }

        return Link.of(template, rel);
    }
}
