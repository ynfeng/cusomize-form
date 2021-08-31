package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class DefaultDataSourceFactory implements DatasourceFactory {
    private final ImmutableMap<Class<? extends Component>, DataSource> dataSourceRegistry =
        ImmutableMap.<Class<? extends Component>, DataSource>builder()
            .put(DepartmentSelect.class, new DepartmentDataSource())
            .build();

    @Override
    public DataSource get(Component component, Map<String, Object> params) {
        return dataSourceRegistry.get(component.getClass());
    }
}
