package com.github.ynfeng.customizeform.customizeform.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ynfeng.customizeform.customizeform.domain.Form;
import com.github.ynfeng.customizeform.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.customizeform.domain.repository.FormRepository;
import com.github.ynfeng.customizeform.customizeform.domain.repository.impl.MemoryFormRepository;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomizeFormServiceTest {
    private final FormRepository formRepository = new MemoryFormRepository();
    private final IDGenerator idGenerator = new DefaultIDGenerator();
    private CustomizeFormService customizeFormService;

    @BeforeEach
    void setup() {
        customizeFormService = new CustomizeFormService(formRepository, idGenerator);
    }

    @Test
    void should_create_form() {
        CreateFormRequest request = CreateFormRequest.builder()
            .name("一个审批表单")
            .companyId("com")
            .items(Lists.newArrayList(
                CreateFormRequest.FormItem.builder()
                    .name("dept")
                    .screenName("选择部门")
                    .type("DepartmentSelect")
                    .build()))
            .build();

        String formId = customizeFormService.create(request);

        Optional<Form> formCandidate = formRepository.find(formId);
        assertThat(formCandidate.isPresent()).isEqualTo(true);

        Form form = formCandidate.get();

        assertThat(form.getItem("dept").isPresent()).isEqualTo(true);
        assertThat(form.getItem("dept").get()).isInstanceOf(DepartmentSelect.class);
    }
}
