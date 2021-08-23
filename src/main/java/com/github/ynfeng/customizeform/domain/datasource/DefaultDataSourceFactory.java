package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.business.Department;
import java.util.Map;

public class DefaultDataSourceFactory implements DatasourceFactory {
    @Override
    public DataSource get(Component component, Map<String, Object> params) {
        return new DataSource() {
            @Override
            public Datas getDatas() {
                Datas datas = new Datas();
                datas.appendData(Data.of("dev", new Department("dev", "研发")));
                datas.appendData(Data.of("market", new Department("market", "市场")));
                return datas;
            }
        };
    }
}
