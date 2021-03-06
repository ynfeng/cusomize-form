package com.github.ynfeng.customizeform.domain.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.datasource.Data;
import com.github.ynfeng.customizeform.domain.datasource.DataSourceStub;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactoryStub;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DepartmentSelectTest {
    private static final Map<String, Object> EMPTY_PARAMS = Maps.newHashMap();

    @Test
    void should_create() {
        DatasourceFactoryStub datasourceFactory = new DatasourceFactoryStub();
        DepartmentSelect departmentSelect = new DepartmentSelect("dept", "部门", datasourceFactory, EMPTY_PARAMS);

        assertThat(departmentSelect.name()).isEqualTo("dept");
        assertThat(departmentSelect.screenName()).isEqualTo("部门");
    }

    @Test
    void should_get_department_options() {
        DataSourceStub dataSourceStub = new DataSourceStub();
        dataSourceStub.addData(Data.of("dev", new Department("dev", "研发")));
        dataSourceStub.addData(Data.of("market", new Department("market", "市场")));

        DatasourceFactoryStub datasourceFactory = new DatasourceFactoryStub();
        datasourceFactory.setDataSource(dataSourceStub);

        DepartmentSelect departmentSelect = new DepartmentSelect("dept", "部门", datasourceFactory, EMPTY_PARAMS);

        List<Department> depts = departmentSelect.getDepartmentOptions();
        assertThat(depts).containsExactly(new Department("dev", "研发"), new Department("market", "市场"));
    }

    @Test
    void deptartment_should_equal_when_same_dept_id() {
        Department dev = new Department("dev", "研发");
        Department otherDev = new Department("dev", "市场");

        assertThat(dev).isEqualTo(otherDev);
    }

}
