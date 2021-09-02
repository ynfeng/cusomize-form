package com.github.ynfeng.customizeform.publish.http;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.business.City;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Relation(collectionRelation = "cityList", itemRelation = "city")
public class CityRepresent extends DataRepresent {
    private final String name;
    private final String code;
    private final String provinceCode;

    private CityRepresent(String name, String code, String provinceCode) {
        this.name = name;
        this.code = code;
        this.provinceCode = provinceCode;
    }

    public static CityRepresent fromDomain(String formId, String itemName, City city) {
        CityRepresent cityRepresent = new CityRepresent(city.getName(), city.getCode(), city.getProvinceCode());

        Map<String, String> params = Maps.newHashMap();
        params.put("type", "area");
        params.put("cityCode", city.getCode());
        Link areaDataLink = linkTo(methodOn(FormDefinitionController.class)
            .getFormDefinitionItemData(formId, itemName, params))
            .withRel("area-data");
        cityRepresent.add(areaDataLink);
        return cityRepresent;
    }
}
