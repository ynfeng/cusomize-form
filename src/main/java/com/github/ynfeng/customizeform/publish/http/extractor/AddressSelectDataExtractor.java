package com.github.ynfeng.customizeform.publish.http.extractor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.publish.http.AreaRepresent;
import com.github.ynfeng.customizeform.publish.http.CityRepresent;
import com.github.ynfeng.customizeform.publish.http.DataRepresent;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionController;
import com.github.ynfeng.customizeform.publish.http.FormDefinitionItemDataSourceRepresent;
import com.github.ynfeng.customizeform.publish.http.ProvinceRepresent;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class AddressSelectDataExtractor extends AbstractDataExtractor {
    @Override
    public FormDefinitionItemDataSourceRepresent extract(String formId, Component formItem, Map<String, String> params) {
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

    private FormDefinitionItemDataSourceRepresent getAreaDataRepresent(String formId, Component formItem, Map<String, String> params) {
        String cityCode = params.get("cityCode");

        Map<String, String> selfLinkParams = makeSelfLinkParams(params);
        selfLinkParams.put("cityCode", cityCode);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), selfLinkParams))
            .withSelfRel();

        FormDefinitionItemDataSourceRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;

        List<DataRepresent> dataRepresentList = addressSelect.getAreaList(cityCode)
            .stream()
            .map(AreaRepresent::fromDomain)
            .collect(Collectors.toList());
        dataRepresent.appendData(dataRepresentList);

        return dataRepresent;
    }

    private FormDefinitionItemDataSourceRepresent getCityDataRepresent(String formId, Component formItem, Map<String, String> params) {
        String provinceCode = params.get("provinceCode");

        Map<String, String> selfLinkParams = makeSelfLinkParams(params);
        selfLinkParams.put("provinceCode", provinceCode);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), selfLinkParams))
            .withSelfRel();

        FormDefinitionItemDataSourceRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;

        List<DataRepresent> dataRepresentList = addressSelect.getCityList(provinceCode).stream()
            .map(city -> CityRepresent.fromDomain(formId, formItem.name(), city))
            .collect(Collectors.toList());
        dataRepresent.appendData(dataRepresentList);

        return dataRepresent;
    }

    private FormDefinitionItemDataSourceRepresent getProvinceDataRepresent(String formId, Component formItem, Map<String, String> params) {
        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(FormDefinitionController.class)
                .getFormDefinitionItemData(formId, formItem.name(), makeSelfLinkParams(params)))
            .withSelfRel();
        FormDefinitionItemDataSourceRepresent dataRepresent = createDataRepresent(formId, selfLink);

        AddressSelect addressSelect = (AddressSelect) formItem;

        List<DataRepresent> dataRepresentList = addressSelect.getProvinceList().stream()
            .map(province -> ProvinceRepresent.fromDomain(formId, formItem.name(), province))
            .collect(Collectors.toList());
        dataRepresent.appendData(dataRepresentList);

        return dataRepresent;
    }

    private Map<String, String> makeSelfLinkParams(Map<String, String> params) {
        Map<String, String> selfLinkParams = Maps.newHashMap();
        selfLinkParams.put("type", params.get("type"));
        return selfLinkParams;
    }
}
