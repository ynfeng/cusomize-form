package com.github.ynfeng.customizeform.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.business.Department;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.SPIDatasourceFactory;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CustomizeFormDefinitionTest {
    private static final Map<String, Object> EMPTY_PARAMS = Maps.newHashMap();

    @Test
    void should_build_customize_form() {
        FormDefinition formDefinition = FormDefinition.withId("1").withName("表单").build();
        DatasourceFactory factory = new SPIDatasourceFactory();

        DepartmentSelect department = new DepartmentSelect("dept", "部门", factory, EMPTY_PARAMS);
        formDefinition.addItem(department);

        Optional<DepartmentSelect> deptSelect = formDefinition.getItem("dept");
        assertThat(deptSelect.isPresent()).isEqualTo(true);

        Department dev = deptSelect.get().getDepartmentOptions().get(0);
        assertThat(dev.getName()).isEqualTo("研发");

    }
}
