package com.github.ynfeng.customizeform.domain.business;

import com.github.ynfeng.customizeform.domain.AbstractComponent;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.select.Option;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentSelect extends AbstractComponent {
    private final SingleSelect<Department> select;

    public DepartmentSelect(String name, String screenName, Map<String, Object> params, DatasourceFactory datasourceFactory) {
        super(name, screenName);
        DataSource datasoure = datasourceFactory.get(this, params);
        select = new SingleSelect(name, screenName, datasoure);
    }

    public List<Department> getDepartmentOptions() {
        return select.getOptions()
            .stream()
            .map(Option::getData)
            .collect(Collectors.toList());
    }
}
