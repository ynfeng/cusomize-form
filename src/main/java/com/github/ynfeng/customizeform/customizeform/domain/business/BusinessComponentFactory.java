package com.github.ynfeng.customizeform.customizeform.domain.business;

import com.github.ynfeng.customizeform.customizeform.domain.Component;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DefaultDataSourceFactory;

public class BusinessComponentFactory {
    private final DatasourceFactory datasourceFactory = new DefaultDataSourceFactory();

    public Component create(String name, String screenName, String companyId, String type) {
        return new DepartmentSelect(name, screenName, companyId, datasourceFactory);
    }
}
