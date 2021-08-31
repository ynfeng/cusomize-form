package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.ImmutableMap;

public class DefaultDataSourceFactory implements DatasourceFactory {
    private final ImmutableMap<String, DataSource> dataSourceRegistry =
        ImmutableMap.<String, DataSource>builder()
            .put("ds_department", new DepartmentDataSource())
            .build();

    @Override
    public DataSource get(String name) {
        return dataSourceRegistry.get(name);
    }
}
