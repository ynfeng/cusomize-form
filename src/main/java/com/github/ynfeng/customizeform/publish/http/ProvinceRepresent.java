package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.business.Province;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "provinceList", itemRelation = "province")
public class ProvinceRepresent extends DataRepresent {
    private final String name;
    private final String code;

    private ProvinceRepresent(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static ProvinceRepresent fromDomain(String formId, String itemName, Province province) {
        ProvinceRepresent provinceRepresent = new ProvinceRepresent(province.getName(), province.getCode());

        Map<String, String> params = new HashMap<>();
        params.put("type", "city");
        params.put("provinceCode", province.getCode());
        Link cityLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItemData(formId, itemName, params))
            .withRel("city-data");
        provinceRepresent.add(cityLink);

        return provinceRepresent;
    }
}
