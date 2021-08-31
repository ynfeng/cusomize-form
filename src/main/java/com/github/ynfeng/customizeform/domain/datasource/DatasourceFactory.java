package com.github.ynfeng.customizeform.domain.datasource;

public interface DatasourceFactory {
    DataSource get(String name);
}
