package com.github.ynfeng.customizeform.domain.datasource;

import java.util.Map;

public interface DataSource {
    Datas getData();

    Datas getData(Map<String, Object> params);
}
