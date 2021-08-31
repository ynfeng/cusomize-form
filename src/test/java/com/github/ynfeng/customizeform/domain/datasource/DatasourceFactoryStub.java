package com.github.ynfeng.customizeform.domain.datasource;

import lombok.Setter;

@Setter
public class DatasourceFactoryStub implements DatasourceFactory {
    private DataSource dataSource;

    @Override
    public DataSource get(String name) {
        return dataSource;
    }
}
