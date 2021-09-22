package com.github.ynfeng.customizeform.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.datasource.SPIDatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.InMemoryFormDefinitionRepository;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomizeFormDefinitionServiceTest {
    private final FormDefinitionRepository formDefinitionRepository = new InMemoryFormDefinitionRepository();
    private final IDGenerator idGenerator = new DefaultIDGenerator();
    private CreateFormDefinitionService customizeFormService;

    @BeforeEach
    void setup() {
        customizeFormService = new CreateFormDefinitionService(formDefinitionRepository, new SPIDatasourceFactory(), idGenerator);
    }

    @Test
    void should_create_form() {
        CreateFormDefinitionRequest request = CreateFormDefinitionRequest.builder()
            .name("一个审批表单")
            .items(Lists.newArrayList(
                CreateFormDefinitionRequest.FormItem.builder()
                    .name("dept")
                    .screenName("选择部门")
                    .type("DepartmentSelect")
                    .build()))
            .build();

        String formId = customizeFormService.create(request);

        Optional<FormDefinition> formCandidate = formDefinitionRepository.find(formId);
        assertThat(formCandidate.isPresent()).isEqualTo(true);

        FormDefinition formDefinition = formCandidate.get();

        assertThat(formDefinition.getItem("dept").isPresent()).isEqualTo(true);
        assertThat(formDefinition.getItem("dept").get()).isInstanceOf(DepartmentSelect.class);
    }
}
