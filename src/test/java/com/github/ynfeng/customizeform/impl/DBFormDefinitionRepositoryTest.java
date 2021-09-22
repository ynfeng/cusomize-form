package com.github.ynfeng.customizeform.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.po.FormDefinitionPo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class DBFormDefinitionRepositoryTest {

    @Autowired
    private FormDefinitionRepository repository;

    @Autowired
    private EntityManager entityManager;

    @TestConfiguration
    static class FormDefinitionRepositoryConfig {
        @Bean
        FormDefinitionRepository formRepository(EntityManager entityManager, DatasourceFactory datasourceFactory) {
            return new DBFormDefinitionRepository(entityManager, datasourceFactory);
        }
    }

    @Test
    void should_save_form_definition() {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("自定义")
            .build();

        repository.save(formDefinition);

        Optional<FormDefinitionPo> formDefPoCandidate = getFormDefinitionPoByFormId("1");
        assertThat(formDefPoCandidate.isPresent()).isTrue();

        FormDefinitionPo formDefPo = formDefPoCandidate.get();
        assertThat(formDefPo.getFormId()).isEqualTo("1");
        assertThat(formDefPo.getName()).isEqualTo("自定义");
        assertThat(formDefPo.getCreateTime()).isNotNull();
        assertThat(formDefPo.getUpdateTime()).isNotNull();
    }

    @Test
    void should_find_all_form_definitions() {
        FormDefinitionPo formDefinitionPo = new FormDefinitionPo();
        formDefinitionPo.setFormId("1");
        formDefinitionPo.setName("自定义1");
        formDefinitionPo.setCreateTime(LocalDateTime.now());
        formDefinitionPo.setUpdateTime(LocalDateTime.now());
        entityManager.persist(formDefinitionPo);

        formDefinitionPo = new FormDefinitionPo();
        formDefinitionPo.setFormId("2");
        formDefinitionPo.setName("自定义2");
        formDefinitionPo.setCreateTime(LocalDateTime.now());
        formDefinitionPo.setUpdateTime(LocalDateTime.now());
        entityManager.persist(formDefinitionPo);

        List<FormDefinition> all = repository.all();
        assertThat(all.size()).isEqualTo(2);

        FormDefinition formDefinition = all.get(0);
        assertThat(formDefinition.formId()).isEqualTo("1");
        assertThat(formDefinition.name()).isEqualTo("自定义1");

        formDefinition = all.get(1);
        assertThat(formDefinition.formId()).isEqualTo("2");
        assertThat(formDefinition.name()).isEqualTo("自定义2");
    }

    @Test
    void should_find_by_form_id() {
        FormDefinitionPo formDefinitionPo = new FormDefinitionPo();
        formDefinitionPo.setFormId("1");
        formDefinitionPo.setName("自定义1");
        formDefinitionPo.setCreateTime(LocalDateTime.now());
        formDefinitionPo.setUpdateTime(LocalDateTime.now());
        entityManager.persist(formDefinitionPo);

        Optional<FormDefinition> formDefinitionCandidate = repository.find("1");
        assertThat(formDefinitionCandidate.isPresent()).isTrue();

        FormDefinition formDefinition = formDefinitionCandidate.get();
        assertThat(formDefinition.name()).isEqualTo("自定义1");
        assertThat(formDefinition.formId()).isEqualTo("1");
    }

    Optional<FormDefinitionPo> getFormDefinitionPoByFormId(String formId) {
        TypedQuery<FormDefinitionPo> query = entityManager.createQuery("select fd from FormDefinitionPo as fd where fd.formId= :formId", FormDefinitionPo.class);
        query.setParameter("formId", formId);
        List<FormDefinitionPo> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            Optional.empty();
        }
        return Optional.of(resultList.get(0));
    }
}
