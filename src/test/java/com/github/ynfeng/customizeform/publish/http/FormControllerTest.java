package com.github.ynfeng.customizeform.publish.http;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ynfeng.customizeform.service.CreateFormRequest;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_create_from() throws Exception {
        CreateFormRequest request = CreateFormRequest.builder()
            .name("my-form")
            .items(Lists.newArrayList(
                CreateFormRequest.FormItem.builder()
                    .name("dept")
                    .screenName("department")
                    .type("DepartmentSelect")
                    .build()
            ))
            .build();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/v1/forms")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(201))
            .andExpect(header().string("Location", startsWith("http://localhost/v1/forms")));
    }
}
