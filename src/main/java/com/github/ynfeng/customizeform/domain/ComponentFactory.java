package com.github.ynfeng.customizeform.domain;

import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;
import com.google.common.collect.Maps;
import java.util.Map;

public class ComponentFactory {
    private final DatasourceFactory datasourceFactory;

    public ComponentFactory(DatasourceFactory datasourceFactory) {
        this.datasourceFactory = datasourceFactory;
    }

    public Component create(String name, String screenName, String type, Map<String, Object> params) {
        switch (type) {
            case "DepartmentSelect":
                return new DepartmentSelect(name, screenName, datasourceFactory, params);
            case "SingleLineText":
                return new SingleLineText(name, screenName);
            case "SingleSelect":
                DataSource dataSource = datasourceFactory.get((String) params.get("dsName"));
                return new SingleSelect(name, screenName, dataSource);
            case "AddressSelect":
                return AddressSelect.with(name, screenName, datasourceFactory)
                    .withProvince((String) params.get("provinceName"), (String) params.get("provinceScreenName"))
                    .withCity((String) params.get("cityName"), (String) params.get("cityScreenName"))
                    .withArea((String) params.get("areaName"), (String) params.get("areaScreenName"))
                    .build();
            default:
                throw new IllegalStateException(String.format("not supported component %s", type));
        }
    }

    public Component create(String name, String screenName, String type) {
        return create(name, screenName, type, Maps.newHashMap());
    }
}
