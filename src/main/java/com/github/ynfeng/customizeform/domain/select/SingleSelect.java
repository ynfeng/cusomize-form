package com.github.ynfeng.customizeform.domain.select;

import com.github.ynfeng.customizeform.domain.AbstractComponent;
import com.github.ynfeng.customizeform.domain.datasource.Data;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.Datas;
import com.google.common.collect.Lists;
import java.util.List;

public class SingleSelect<T> extends AbstractComponent {
    private final DataSource dataSource;

    public SingleSelect(String name, String screenName, DataSource dataSource) {
        super(name, screenName);
        this.dataSource = dataSource;
    }

    public List<Option<T>> getOptions() {
        Datas datas = dataSource.getDatas();

        List<Option<T>> result = Lists.newArrayList();

        for (Data data : datas) {
            Option option = Option.of(data.name(), data.value());
            result.add(option);
        }

        return result;
    }
}
