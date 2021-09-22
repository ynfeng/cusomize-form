package com.github.ynfeng.customizeform.domain.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
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
        FormDefinitionRepository formRepository(EntityManager entityManager) {
            return new DBFormDefinitionRepository(entityManager);
        }
    }

    @Test
    void should_save() {
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
