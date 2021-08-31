package com.github.ynfeng.customizeform.publish.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.Datas;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AddressSelectTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormDefinitionRepository formDefinitionRepository;

    @MockBean
    private DatasourceFactory datasourceFactory;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_create_form_definition_with_address_select() throws Exception {
        DataSource provinceDataSource = mock(DataSource.class);
        when(provinceDataSource.getData())
            .thenReturn(new Datas());

        DataSource cityDataSource = mock(DataSource.class);
        when(cityDataSource.getData(any()))
            .thenReturn(new Datas());

        DataSource areaDataSource = mock(DataSource.class);
        when(areaDataSource.getData(any()))
            .thenReturn(new Datas());

        when(datasourceFactory.get("ds_province"))
            .thenReturn(provinceDataSource);
        when(datasourceFactory.get("ds_city"))
            .thenReturn(cityDataSource);
        when(datasourceFactory.get("ds_area"))
            .thenReturn(areaDataSource);

        CreateFormDefinitionRequest request = CreateFormDefinitionRequest.builder()
            .name("my-form")
            .items(Lists.newArrayList(
                CreateFormDefinitionRequest.FormItem.builder()
                    .name("address")
                    .screenName("select an address")
                    .type("AddressSelect")
                    .params(ImmutableMap.<String, Object>builder()
                        .put("provinceName", "province")
                        .put("provinceScreenName", "select a province")
                        .put("cityName", "city")
                        .put("cityScreenName", "select a city")
                        .put("areaName", "area")
                        .put("areaScreenName", "select a area")
                        .build())
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

        FormDefinition formDefinition = formDefinitionRepository.find("1").get();
        Optional<AddressSelect> addressSelectCandidate = formDefinition.getItem("address");
        assertThat(addressSelectCandidate.isPresent()).isTrue();

        AddressSelect addressSelect = addressSelectCandidate.get();
        addressSelect.getProvinceList();
        addressSelect.getCityList("BJ");
        addressSelect.getAreaList("HD");

        verify(provinceDataSource, times(1)).getData();
        verify(cityDataSource, times(1)).getData(
            ImmutableMap.<String, Object>builder()
                .put("provinceCode", "BJ")
                .build());
        verify(areaDataSource, times(1)).getData(
            ImmutableMap.<String, Object>builder()
                .put("cityCode", "HD")
                .build());
    }
}
