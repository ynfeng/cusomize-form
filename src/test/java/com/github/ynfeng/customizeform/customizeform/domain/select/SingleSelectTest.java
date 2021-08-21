package com.github.ynfeng.customizeform.customizeform.domain.select;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.customizeform.domain.User;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.Data;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DataSourceStub;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DummyDataSource;
import java.util.List;
import org.junit.jupiter.api.Test;

class SingleSelectTest {

    @Test
    void should_create() {
        DataSource dataSource = new DummyDataSource();
        SingleSelect<String> singleSelect = new SingleSelect<>("ss1", "这是一个单选下拉框", dataSource);

        assertThat(singleSelect.name()).isEqualTo("ss1");
        assertThat(singleSelect.screenName()).isEqualTo("这是一个单选下拉框");
    }

    @Test
    void should_get_multi_options() {
        DataSourceStub dataSource = new DataSourceStub();
        dataSource.addData(Data.of("opt1", "val1"));
        dataSource.addData(Data.of("opt2", "val2"));

        SingleSelect<String> singleSelect = new SingleSelect<>("ss1", "这是一个单选下拉框", dataSource);

        List<Option<String>> options = singleSelect.getOptions();

        assertThat(options).containsExactly(
            Option.of("opt1", "val1"),
            Option.of("opt2", "val2"));
    }

    @Test
    void should_get_single_option() {
        DataSourceStub dataSource = new DataSourceStub();
        dataSource.addData(Data.of("opt1", "val1"));

        SingleSelect<String> singleSelect = new SingleSelect<>("ss1", "这是一个单选下拉框", dataSource);
        List<Option<String>> options = singleSelect.getOptions();

        assertThat(options).containsExactly(Option.of("opt1", "val1"));
    }

    @Test
    void option_should_be_object() {
        DataSourceStub dataSource = new DataSourceStub();
        dataSource.addData(Data.of("opt1", new User("zhangsan")));

        SingleSelect<User> singleSelect = new SingleSelect<>("ss1", "这是一个单选下拉框", dataSource);
        List<Option<User>> options = singleSelect.getOptions();

        Option<User> userOption = options.get(0);
        assertThat(userOption.getData()).isEqualTo(new User("zhangsan"));
    }
}
