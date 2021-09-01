package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataRepresent;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class AddressSelectDataExtractor extends AbstractDataExtractor {
    @Override
    public FormDefinitionItemDataRepresent extract(String formId, Component formItem, Map<String, String> params) {
        String type = params.get("type");
        switch (type) {
            case "province":
                return getProvinceDataRepresent(formId, formItem, params);
            case "city":
                return getCityDataRepresent(formId, formItem, params);
            case "area":
                return getAreaDataRepresent(formId, formItem, params);
            default:
                throw new IllegalStateException("Unsupported address datasource.");
        }

    }

    private FormDefinitionItemDataRepresent getAreaDataRepresent(String formId, Component formItem, Map<String, String> params) {
        String cityCode = params.get("cityCode");

        Map<String, String> selfLinkParams = makeSelfLinkParams(params);
        selfLinkParams.put("cityCode", cityCode);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), selfLinkParams))
            .withSelfRel();

        FormDefinitionItemDataRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;
        addressSelect.getAreaList(cityCode).stream().forEach(dataRepresent::appendData);

        return dataRepresent;
    }

    private FormDefinitionItemDataRepresent getCityDataRepresent(String formId, Component formItem, Map<String, String> params) {
        String provinceCode = params.get("provinceCode");

        Map<String, String> selfLinkParams = makeSelfLinkParams(params);
        selfLinkParams.put("provinceCode", provinceCode);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), selfLinkParams))
            .withSelfRel();

        FormDefinitionItemDataRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;
        addressSelect.getCityList(provinceCode).stream().forEach(dataRepresent::appendData);

        return dataRepresent;
    }

    private FormDefinitionItemDataRepresent getProvinceDataRepresent(String formId, Component formItem, Map<String, String> params) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), makeSelfLinkParams(params)))
            .withSelfRel();

        FormDefinitionItemDataRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;
        addressSelect.getProvinceList().stream().forEach(dataRepresent::appendData);

        return dataRepresent;
    }

    private Map<String, String> makeSelfLinkParams(Map<String, String> params) {
        Map<String, String> selfLinkParams = Maps.newHashMap();
        selfLinkParams.put("type", params.get("type"));
        return selfLinkParams;
    }
}
