package com.github.ynfeng.customizeform.customizeform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.customizeform.domain.datasource.DefaultDataSourceFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CustomizeFormTest {

    @Test
    void should_build_customize_form() {
        Form form = new Form("1", "表单");
        DatasourceFactory factory = new DefaultDataSourceFactory();

        DepartmentSelect department = new DepartmentSelect("dept", "部门", "com", factory);
        form.addItem(department);

        Optional<DepartmentSelect> deptSelect = form.getItem("dept");
        assertThat(deptSelect.isPresent()).isEqualTo(true);

        Department dev = deptSelect.get().getDepartmentOptions().get(0);
        assertThat(dev.getName()).isEqualTo("研发");

    }
}
