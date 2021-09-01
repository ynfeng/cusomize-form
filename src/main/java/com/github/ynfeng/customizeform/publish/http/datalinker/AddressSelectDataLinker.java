package com.github.ynfeng.customizeform.publish.http.datalinker;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemRepresent;
import com.github.ynfeng.customizeform.publish.http.TemplateLink;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.hateoas.Link;

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
        Link cityDataLink = TemplateLink.create(linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), params)),
            "city-data", "provinceCode");
        formItemRepresent.add(cityDataLink);

        params = Maps.newHashMap();
        params.put("type", "area");
        Link areaDataLink = TemplateLink.create(linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), params)),
            "area-data", "cityCode");
        formItemRepresent.add(areaDataLink);
    }

}
