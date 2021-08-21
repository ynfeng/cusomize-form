package com.github.ynfeng.customizeform.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.customizeform.domain.Component;

public interface DatasourceFactory {
    DataSource get(String companyId, Component component);
}
