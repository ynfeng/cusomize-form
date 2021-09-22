package com.github.ynfeng.customizeform.domain.datasource;

import com.github.ynfeng.customizeform.domain.business.Department;
import java.util.Map;

public class DepartmentDataSource implements DataSource {
    @Override
    public String name() {
        return "ds_department";
    }

    @Override
    public Datas getData() {
        Datas datas = new Datas();
        datas.appendData(Data.of("dev", new Department("dev", "研发")));
        datas.appendData(Data.of("market", new Department("market", "市场")));
        return datas;
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        return getData();
    }
}
