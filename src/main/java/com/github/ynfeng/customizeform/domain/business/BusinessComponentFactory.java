package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;
import com.google.common.collect.Maps;
import java.util.Map;

public class BusinessComponentFactory {
    private final DatasourceFactory datasourceFactory;

    public BusinessComponentFactory(DatasourceFactory datasourceFactory) {
        this.datasourceFactory = datasourceFactory;
    }

    public Component create(String name, String screenName, String type, Map<String, Object> params) {
        switch (type) {
            case "DepartmentSelect":
                return new DepartmentSelect(name, screenName, datasourceFactory, params);
            case "SingleLineText":
                return new SingleLineText(name, screenName);
            case "AddressSelect":
                return AddressSelect.with(name, screenName, datasourceFactory)
                    .withProvince((String) params.get("provinceName"), (String) params.get("provinceScreenName"))
                    .withCity((String) params.get("cityName"), (String) params.get("cityScreenName"))
                    .withArea((String) params.get("areaName"), (String) params.get("areaScreenName"))
                    .build();
            default:
                return null;
        }
    }

    public Component create(String name, String screenName, String type) {
        return create(name, screenName, type, Maps.newHashMap());
    }
}
