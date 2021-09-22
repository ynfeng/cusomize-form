package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.ServiceLoader;

public class SPIDatasourceFactory implements DatasourceFactory {
    private final Map<String, DataSource> dataSourceHolder = Maps.newHashMap();

    @Override
    public DataSource get(String name) {
        synchronized (dataSourceHolder) {
            if (dataSourceHolder.isEmpty()) {
                loadDataSource();
            }

            DataSource datasource = dataSourceHolder.get(name);
            if (datasource == null) {
                throw new IllegalStateException(String.format("not found datasource %s", name));
            }

            return datasource;
        }
    }

    private void loadDataSource() {
        ServiceLoader<DataSource> dataSourceLoader = ServiceLoader.load(DataSource.class);

        for (DataSource ds : dataSourceLoader) {
            dataSourceHolder.put(ds.name(), ds);
        }
    }
}
