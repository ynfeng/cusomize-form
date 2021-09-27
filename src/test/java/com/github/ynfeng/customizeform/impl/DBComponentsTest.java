package com.github.ynfeng.customizeform.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.DummyDataSource;
import com.github.ynfeng.customizeform.domain.datasource.PropertyDataSource;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;
import com.github.ynfeng.customizeform.impl.po.FormDefinitionComponentPo;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class DBComponentsTest {

    @Autowired
    private FormDefinitionRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DatasourceFactory datasourceFactory;

    @Test
    void should_save_single_line_text() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        repository.save(formDefinition);

        formDefinition.addItem(new SingleLineText("single_line_text", "单行文本"));

        Optional<FormDefinitionComponentPo> componentPoCandidate = getComponentByFormId("1", "single_line_text");
        assertThat(componentPoCandidate.isPresent()).isTrue();

        FormDefinitionComponentPo po = componentPoCandidate.get();
        assertThat(po.getFormId()).isEqualTo("1");
        assertThat(po.getName()).isEqualTo("single_line_text");
        assertThat(po.getScreenName()).isEqualTo("单行文本");
        assertThat(po.getExtraData()).isEqualTo("{}");
        assertThat(po.getDsName()).isEqualTo("");
        assertThat(po.getType()).isEqualTo("SingleLineText");
        assertThat(po.getCreateTime()).isNotNull();
    }

    @Test
    void should_save_with_datasource_component() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        repository.save(formDefinition);

        formDefinition.addItem(new SingleSelect<>("single_select", "单选", new DummyDataSource()));

        Optional<FormDefinitionComponentPo> componentPoCandidate = getComponentByFormId("1", "single_select");
        assertThat(componentPoCandidate.isPresent()).isTrue();

        FormDefinitionComponentPo po = componentPoCandidate.get();
        assertThat(po.getFormId()).isEqualTo("1");
        assertThat(po.getName()).isEqualTo("single_select");
        assertThat(po.getScreenName()).isEqualTo("单选");
        assertThat(po.getDsName()).isEqualTo("ds_dummy");
        assertThat(po.getCreateTime()).isNotNull();
        assertThat(po.getType()).isEqualTo("SingleSelect");
    }

    @Test
    void should_get_all_form_definition_items() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        formDefinition.addItem(new SingleSelect<>("single_select", "单选", new PropertyDataSource()));
        formDefinition.addItem(new SingleLineText("single_line_text", "单行文本"));
        formDefinition.addItem(new DepartmentSelect("department_select", "部门", datasourceFactory));
        formDefinition.addItem(AddressSelect
            .with("address_select", "地址", datasourceFactory)
            .withCity("city_select", "城市")
            .withProvince("province_select", "省")
            .withArea("area_select", "地区")
            .build());
        repository.save(formDefinition);

        formDefinition = repository.find("1").get();
        List<Component> allItems = formDefinition.items();
        assertThat(allItems.size()).isEqualTo(4);

        SingleSelect singleSelect = (SingleSelect) allItems.get(0);
        assertThat(singleSelect.name()).isEqualTo("single_select");
        assertThat(singleSelect.screenName()).isEqualTo("单选");

        SingleLineText singleLineText = (SingleLineText) allItems.get(1);
        assertThat(singleLineText.name()).isEqualTo("single_line_text");
        assertThat(singleLineText.screenName()).isEqualTo("单行文本");

        DepartmentSelect departmentSelect = (DepartmentSelect) allItems.get(2);
        assertThat(departmentSelect.name()).isEqualTo("department_select");
        assertThat(departmentSelect.screenName()).isEqualTo("部门");

        AddressSelect addressSelect = (AddressSelect) allItems.get(3);
        assertThat(addressSelect.name()).isEqualTo("address_select");
        assertThat(addressSelect.screenName()).isEqualTo("地址");
    }

    @Test
    void should_get_form_definition_item() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        formDefinition.addItem(new SingleSelect<>("single_select", "单选", new PropertyDataSource()));
        repository.save(formDefinition);

        formDefinition = repository.find("1").get();
        SingleSelect<String> singleSelect = (SingleSelect<String>) formDefinition.getItem("single_select").get();
        assertThat(singleSelect).isNotNull();
    }

    Optional<FormDefinitionComponentPo> getComponentByFormId(String formId, String name) {
        TypedQuery<FormDefinitionComponentPo> query = entityManager.createQuery("select c from FormDefinitionComponentPo as c where c.formId = :formId and c.name = :name", FormDefinitionComponentPo.class);
        query.setParameter("formId", formId);
        query.setParameter("name", name);

        List<FormDefinitionComponentPo> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }
}
