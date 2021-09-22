package com.github.ynfeng.customizeform.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.FormDefinition;
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
import org.junit.jupiter.api.Disabled;
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
        assertThat(po.getExtraData()).isEqualTo("");
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
    @Disabled
    void should_get_all_form_definition_items() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        formDefinition.addItem(new SingleSelect<>("single_select", "单选", new PropertyDataSource()));
        formDefinition.addItem(new SingleLineText("single_line_text", "单行文本"));
        formDefinition.addItem(new DepartmentSelect("department_select", "部门", datasourceFactory));
        repository.save(formDefinition);

        formDefinition = repository.find("1").get();
        List<Component> allItems = formDefinition.items();
        assertThat(allItems.size()).isEqualTo(3);

        SingleSelect singleSelect = (SingleSelect) allItems.get(0);
        assertThat(singleSelect.name()).isEqualTo("single_select");
        assertThat(singleSelect.screenName()).isEqualTo("单选");

        DepartmentSelect departmentSelect = (DepartmentSelect) allItems.get(1);
        assertThat(departmentSelect.name()).isEqualTo("single_line_text");
        assertThat(departmentSelect.screenName()).isEqualTo("单行文本");

        SingleLineText singleLineText = (SingleLineText) allItems.get(1);
        assertThat(singleLineText.name()).isEqualTo("department_select");
        assertThat(singleLineText.screenName()).isEqualTo("部门");
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
