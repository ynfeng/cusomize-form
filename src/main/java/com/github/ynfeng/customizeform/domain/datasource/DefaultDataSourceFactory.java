package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.ImmutableMap;

public class DefaultDataSourceFactory implements DatasourceFactory {
    private final ImmutableMap<String, DataSource> dataSourceRegistry =
        ImmutableMap.<String, DataSource>builder()
            .put("ds_department", new DepartmentDataSource())
            .put("ds_province", new ProvinceDataSource())
            .put("ds_city", new CityDataSource())
            .put("ds_area", new AreaDataSource())
            .build();

    @Override
    public DataSource get(String name) {
        return dataSourceRegistry.get(name);
    }
}
