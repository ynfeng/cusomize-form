package com.github.ynfeng.customizeform.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CustomizeFormDefinitionServiceTest {
    @Autowired
    private CreateFormDefinitionService customizeFormService;

    @Autowired
    private FormDefinitionRepository formDefinitionRepository;

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
