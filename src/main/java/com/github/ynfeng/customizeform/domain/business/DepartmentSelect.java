package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.AbstractComponent;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.select.Option;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentSelect extends AbstractComponent {
    private final SingleSelect<Department> select;

    public DepartmentSelect(String name, String screenName, DatasourceFactory datasourceFactory, Map<String, Object> params) {
        super(name, screenName);
        DataSource datasource = datasourceFactory.get("ds_department");
        select = new SingleSelect(name, screenName, datasource);
    }

    public DepartmentSelect(String name, String screenName, DatasourceFactory datasourceFactory) {
        this(name, screenName, datasourceFactory, Maps.newHashMap());
    }

    public List<Department> getDepartmentOptions() {
        return select.getOptions()
            .stream()
            .map(Option::getData)
            .collect(Collectors.toList());
    }
}
