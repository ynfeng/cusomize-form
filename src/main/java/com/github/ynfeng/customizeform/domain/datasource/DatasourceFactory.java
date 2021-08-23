package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.Component;
import java.util.Map;

public interface DatasourceFactory {
    DataSource get(Component component, Map<String, Object> params);
}
