package com.github.ynfeng.customizeform.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.customizeform.domain.Component;
import java.util.Map;
import lombok.Setter;

@Setter
public class DatasourceFactoryStub implements DatasourceFactory {
    private DataSource dataSource;

    @Override
    public DataSource get(Component component, Map<String, Object> params) {
        return dataSource;
    }
}
