package com.github.ynfeng.customizeform.domain.datasource;

import java.util.Map;

public class DummyDataSource implements DataSource {
    @Override
    public String name() {
        return "ds_dummy";
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
