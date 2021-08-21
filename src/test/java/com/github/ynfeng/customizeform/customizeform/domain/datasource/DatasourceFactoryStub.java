package com.github.ynfeng.customizeform.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.customizeform.domain.Component;
import lombok.Setter;

@Setter
public class DatasourceFactoryStub implements DatasourceFactory {
    private DataSource dataSource;

    @Override
    public DataSource get(String companyId, Component component) {
        return dataSource;
    }
}
