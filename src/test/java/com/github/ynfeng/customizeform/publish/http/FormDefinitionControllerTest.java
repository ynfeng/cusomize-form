package com.github.ynfeng.customizeform.publish.http;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.DepartmentSelect;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.SPIDatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionRequest;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class FormDefinitionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormDefinitionRepository formDefinitionRepository;

    private final DatasourceFactory datasourceFactory = new SPIDatasourceFactory();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_create_from_definition() throws Exception {
        when(formDefinitionRepository.save(any(FormDefinition.class)))
            .then(returnsFirstArg());

        CreateFormDefinitionRequest request = CreateFormDefinitionRequest.builder()
            .name("my-form")
            .items(Lists.newArrayList(
                CreateFormDefinitionRequest.FormItem.builder()
                    .name("dept")
                    .screenName("department")
                    .type("DepartmentSelect")
                    .build()
            ))
            .build();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/form-definitions")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(201))
            .andExpect(header().string("Location", startsWith("http://localhost/v1/form-definition")));
    }

    @Test
    void should_404_when_get_not_exist_from_definition() throws Exception {
        mockMvc.perform(get("/v1/form-definitions/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(404));
    }

    @Test
    void should_get_exist_form_definition() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("a form")
            .build();
        when(formDefinitionRepository.find("1")).thenReturn(Optional.of(formDefinition));

        mockMvc.perform(get("/v1/form-definitions/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/1")))
            .andExpect(jsonPath("$._links.form-definition-items.href", is("http://localhost/v1/form-definitions/1/form-definition-items")));
    }

    @Test
    void should_404_when_get_not_exist_from_items() throws Exception {
        when(formDefinitionRepository.find("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/forms/1/form-definition-items")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(404));
    }

    @Test
    void should_get_exist_form_items() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("my form")
            .build();

        DepartmentSelect depSel = new DepartmentSelect("dept", "department", datasourceFactory);
        formDefinition.addItem(depSel);

        when(formDefinitionRepository.find("1")).thenReturn(Optional.of(formDefinition));

        mockMvc.perform(get("/v1/form-definitions/1/form-definition-items")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/1/form-definition-items")))
            .andExpect(jsonPath("$._embedded.form-definition-items[0]._links.data.href", is("http://localhost/v1/form-definitions/1/form-definition-items/dept/data")))
            .andExpect(jsonPath("$._embedded.form-definition-items[0].name", is("dept")))
            .andExpect(jsonPath("$._embedded.form-definition-items[0].screenName", is("department")))
            .andExpect(jsonPath("$._embedded.form-definition-items[0].type", is("DepartmentSelect")))
            .andExpect(status().is(200));
    }

    @Test
    void should_404_when_get_not_exist_form_item_data() throws Exception {
        mockMvc.perform(get("/v1/form-definitions/1/form-definitions-items/dept/data")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(404));
    }

    @Test
    void should_404_when_get_not_exist_form_item_data_from_exist_form() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("my form")
            .build();
        when(formDefinitionRepository.find("1")).thenReturn(Optional.of(formDefinition));

        mockMvc.perform(get("/v1/form-definitions/1/form-definitions-items/dept/data")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(404));
    }

    @Test
    void should_get_form_item_data() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("my form")
            .build();

        DepartmentSelect depSel = new DepartmentSelect("dept", "department", datasourceFactory);
        formDefinition.addItem(depSel);

        when(formDefinitionRepository.find("1")).thenReturn(Optional.of(formDefinition));

        mockMvc.perform(get("/v1/form-definitions/1/form-definition-items/dept/data")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.datasource._embedded.departmentList.length()", is(2)))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/1/form-definition-items/dept/data")))
            .andExpect(jsonPath("$._links.form-definition.href", is("http://localhost/v1/form-definitions/1")));
    }

    @Test
    void should_get_all_form_definitions() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("my form")
            .build();
        when(formDefinitionRepository.all()).thenReturn(Lists.newArrayList(formDefinition));

        mockMvc.perform(get("/v1/form-definitions")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions")))
            .andExpect(jsonPath("$._embedded.form-definitions.length()", is(1)))
            .andExpect(status().is(200));
    }
}
