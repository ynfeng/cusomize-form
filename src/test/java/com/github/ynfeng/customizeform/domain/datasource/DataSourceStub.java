package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;

public class DataSourceStub implements DataSource {
    private List<Data> dataList = Lists.newArrayList();

    @Override
    public String name() {
        return "ds_stub";
    }

    @Override
    public Datas getData() {
        Datas datas = new Datas();

        dataList.stream().forEach(datas::appendData);

        return datas;
    }

    @Override
    public Datas getData(Map<String, Object> params) {
        return getData();
    }

    public void addData(Data data) {
        dataList.add(data);
    }
}
