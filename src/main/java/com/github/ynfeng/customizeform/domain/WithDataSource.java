package com.github.ynfeng.customizeform.domain;

import com.github.ynfeng.customizeform.domain.datasource.DataSource;

public interface WithDataSource extends Component {
    DataSource datasource();
}
