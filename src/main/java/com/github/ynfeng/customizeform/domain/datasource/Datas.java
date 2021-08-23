package com.github.ynfeng.customizeform.domain.datasource;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class Datas implements Iterable<Data> {
    private List<Data> dataList = Lists.newArrayList();

    @Override
    public Iterator<Data> iterator() {
        return dataList.iterator();
    }

    public void appendData(Data data) {
        dataList.add(data);
    }
}
