package com.github.ynfeng.customizeform.domain.datasource;

import java.util.Map;

public class PropertyDataSource implements DataSource {

    @Override
    public String name() {
        return "ds_property";
    }

    @Override
    public Datas getData() {
        return null;
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        return null;
    }
}
