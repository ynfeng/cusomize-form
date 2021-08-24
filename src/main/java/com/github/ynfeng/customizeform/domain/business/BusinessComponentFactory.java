package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.DefaultDataSourceFactory;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;
import java.util.Map;

public class BusinessComponentFactory {
    private final DatasourceFactory datasourceFactory = new DefaultDataSourceFactory();

    public Component create(String name, String screenName, String type, Map<String, Object> params) {
        switch (type) {
            case "DepartmentSelect":
                return new DepartmentSelect(name, screenName, datasourceFactory, params);
            case "SingleLineText":
                return new SingleLineText(name, screenName);
            default:
                return null;
        }
    }
}
