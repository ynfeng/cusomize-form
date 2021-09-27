package com.github.ynfeng.customizeform.publish.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.domain.business.Area;
import com.github.ynfeng.customizeform.domain.business.City;
import com.github.ynfeng.customizeform.domain.business.Province;
import com.github.ynfeng.customizeform.domain.datasource.Data;
import com.github.ynfeng.customizeform.domain.datasource.DataSource;
import com.github.ynfeng.customizeform.domain.datasource.DataSourceStub;
import com.github.ynfeng.customizeform.domain.datasource.Datas;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.DBComponents;
import com.github.ynfeng.customizeform.service.CreateFormDefinitionRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.Optional;
import javax.persistence.EntityManager;
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
class AddressSelectTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormDefinitionRepository formDefinitionRepository;

    @MockBean
    private DatasourceFactory datasourceFactory;

    @Autowired
    private EntityManager entityManager;

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

        FormDefinition formDefinition = formDefinitionRepository.all().get(0);
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

    @Test
    void should_contains_data_links() throws Exception {
        FormDefinition formDefinition = FormDefinition
            .withId("1")
            .withName("a form")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        AddressSelect addressSelect = AddressSelect
            .with("address", "select address", datasourceFactory)
            .withArea("area", "select a area")
            .withProvince("province", "select a province")
            .withCity("city", "select a city")
            .build();
        formDefinition.addItem(addressSelect);
        formDefinitionRepository.save(formDefinition);

        mockMvc.perform(get("/v1/form-definitions/1/form-definition-items")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded['form-definition-items'][0]._links['province-data'].href",
                is("http://localhost/v1/form-definitions/1/form-definition-items/address/data?type=province")));
    }

    @Test
    void should_get_province_data() throws Exception {
        DataSourceStub provinceDataSource = new DataSourceStub();
        provinceDataSource.addData(Data.of("BJ", new Province("北京", "BJ")));
        provinceDataSource.addData(Data.of("HB", new Province("河北", "HB")));
        when(datasourceFactory.get("ds_province"))
            .thenReturn(provinceDataSource);

        FormDefinition formDefinition = FormDefinition
            .withId("3")
            .withName("a form")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        AddressSelect addressSelect = AddressSelect
            .with("address", "select address", datasourceFactory)
            .withArea("area", "select a area")
            .withProvince("province", "select a province")
            .withCity("city", "select a city")
            .build();
        formDefinition.addItem(addressSelect);
        formDefinition = formDefinitionRepository.save(formDefinition);

        mockMvc.perform(get("/v1/form-definitions/" + formDefinition.formId() + "/form-definition-items/address/data?type=province")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.datasource._embedded.provinceList.length()", is(2)))
            .andExpect(jsonPath("$.datasource._embedded.provinceList[0].code", is("BJ")))
            .andExpect(jsonPath("$.datasource._embedded.provinceList[1].code", is("HB")))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/3/form-definition-items/address/data?type=province")))
            .andExpect(jsonPath("$.datasource._embedded.provinceList[0]._links.city-data.href", is("http://localhost/v1/form-definitions/3/form-definition-items/address/data?provinceCode=BJ&type=city")))
            .andExpect(jsonPath("$.datasource._embedded.provinceList[1]._links.city-data.href", is("http://localhost/v1/form-definitions/3/form-definition-items/address/data?provinceCode=HB&type=city")))
            .andExpect(jsonPath("$._links.form-definition.href", is("http://localhost/v1/form-definitions/3")));
    }

    @Test
    void should_get_city_data() throws Exception {
        DataSourceStub cityDataSource = new DataSourceStub();
        cityDataSource.addData(Data.of("HB", new City("HB", "廊坊", "LF")));
        when(datasourceFactory.get("ds_city"))
            .thenReturn(cityDataSource);

        FormDefinition formDefinition = FormDefinition
            .withId("2")
            .withName("a form")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));

        AddressSelect addressSelect = AddressSelect
            .with("address", "select address", datasourceFactory)
            .withArea("area", "select a area")
            .withProvince("province", "select a province")
            .withCity("city", "select a city")
            .build();
        formDefinition.addItem(addressSelect);
        formDefinition = formDefinitionRepository.save(formDefinition);

        mockMvc.perform(get("/v1/form-definitions/" + formDefinition.formId() + "/form-definition-items/address/data?type=city&provinceCode=HB")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.datasource._embedded.cityList.length()", is(1)))
            .andExpect(jsonPath("$.datasource._embedded.cityList[0].code", is("LF")))
            .andExpect(jsonPath("$.datasource._embedded.cityList[0]._links.area-data.href", is("http://localhost/v1/form-definitions/2/form-definition-items/address/data?cityCode=LF&type=area")))
            .andExpect(jsonPath("$.datasource._embedded.cityList[0].provinceCode", is("HB")))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/2/form-definition-items/address/data?provinceCode=HB&type=city")))
            .andExpect(jsonPath("$._links.form-definition.href", is("http://localhost/v1/form-definitions/2")));
    }

    @Test
    void should_get_area_data() throws Exception {
        DataSourceStub areaDataSource = new DataSourceStub();
        areaDataSource.addData(Data.of("HB", new Area("LF", "文安县", "WA")));
        when(datasourceFactory.get("ds_area"))
            .thenReturn(areaDataSource);

        FormDefinition formDefinition = FormDefinition
            .withId("4")
            .withName("a form")
            .build();
        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));
        AddressSelect addressSelect = AddressSelect
            .with("address", "select address", datasourceFactory)
            .withArea("area", "select a area")
            .withProvince("province", "select a province")
            .withCity("city", "select a city")
            .build();
        formDefinition.addItem(addressSelect);
        formDefinition = formDefinitionRepository.save(formDefinition);

        mockMvc.perform(get("/v1/form-definitions/" + formDefinition.formId() + "/form-definition-items/address/data?type=area&cityCode=LF")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.datasource._embedded.areaList.length()", is(1)))
            .andExpect(jsonPath("$.datasource._embedded.areaList[0].code", is("WA")))
            .andExpect(jsonPath("$.datasource._embedded.areaList[0].cityCode", is("LF")))
            .andExpect(jsonPath("$._links.self.href", is("http://localhost/v1/form-definitions/4/form-definition-items/address/data?cityCode=LF&type=area")))
            .andExpect(jsonPath("$._links.form-definition.href", is("http://localhost/v1/form-definitions/4")));
    }
}
