package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.Lists;
import java.util.List;

public class DataSourceStub implements DataSource {
    private List<Data> dataList = Lists.newArrayList();

    @Override
    public Datas getDatas() {
        Datas datas = new Datas();

        dataList.stream().forEach(datas::appendData);

        return datas;
    }

    public void addData(Data data) {
        dataList.add(data);
    }
}
