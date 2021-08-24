package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.DefaultDataSourceFactory;
import java.util.Map;

public class BusinessComponentFactory {
    private final DatasourceFactory datasourceFactory = new DefaultDataSourceFactory();

    public Component create(String name, String screenName, String type, Map<String, Object> params) {
        return new DepartmentSelect(name, screenName, datasourceFactory, params);
    }
}
