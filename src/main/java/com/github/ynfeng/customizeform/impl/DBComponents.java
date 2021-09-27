package com.github.ynfeng.customizeform.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.ComponentFactory;
import com.github.ynfeng.customizeform.domain.Components;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.WithDataSource;
import com.github.ynfeng.customizeform.domain.business.AddressSelect;
import com.github.ynfeng.customizeform.domain.business.Area;
import com.github.ynfeng.customizeform.domain.business.City;
import com.github.ynfeng.customizeform.domain.business.Province;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.select.SingleSelect;
import com.github.ynfeng.customizeform.impl.po.FormDefinitionComponentPo;
import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class DBComponents implements Components {
    private final EntityManager entityManager;
    private final FormDefinition formDefinition;
    private final ComponentFactory componentFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DBComponents(FormDefinition formDefinition, EntityManager entityManager, DatasourceFactory datasourceFactory) {
        this.entityManager = entityManager;
        this.formDefinition = formDefinition;
        this.componentFactory = new ComponentFactory(datasourceFactory);
    }

    @Override
    public void add(Component component) {
        FormDefinitionComponentPo po = new FormDefinitionComponentPo();
        po.setName(component.name());
        po.setScreenName(component.screenName());
        po.setType(component.getClass().getSimpleName());
        po.setFormId(formDefinition.formId());
        po.setCreateTime(LocalDateTime.now());

        if (component instanceof WithDataSource) {
            WithDataSource withDataSource = (WithDataSource) component;
            po.setDsName(withDataSource.datasource().name());
        }

        Map<String, Object> extraData = Maps.newHashMap();
        if (component instanceof AddressSelect) {
            AddressSelect addressSelect = (AddressSelect) component;
            SingleSelect<Province> provinceSelect = addressSelect.getProvinceSelect();
            SingleSelect<City> citySelect = addressSelect.getCitySelect();
            SingleSelect<Area> areaSelect = addressSelect.getAreaSelect();

            extraData.put(AddressSelect.PROVINCE_NAME, provinceSelect.name());
            extraData.put(AddressSelect.PROVINCE_SCREEN_NAME, provinceSelect.screenName());
            extraData.put(AddressSelect.CITY_NAME, citySelect.name());
            extraData.put(AddressSelect.CITY_SCREEN_NAME, citySelect.screenName());
            extraData.put(AddressSelect.AREA_NAME, areaSelect.name());
            extraData.put(AddressSelect.AREA_SCREEN_NAME, addressSelect.screenName());
        }

        try {
            po.setExtraData(objectMapper.writeValueAsString(extraData));
        } catch (JsonProcessingException e) {
            throw new PersistenceException(e);
        }

        entityManager.persist(po);
    }

    @Override
    public List<Component> all() {
        TypedQuery<FormDefinitionComponentPo> query = entityManager.createQuery("select c from FormDefinitionComponentPo c where c.formId=:formId", FormDefinitionComponentPo.class);
        List<FormDefinitionComponentPo> pos = query.setParameter("formId", formDefinition.formId()).getResultList();
        return pos.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Component toDomain(FormDefinitionComponentPo po) {
        Map<String, Object> params;

        try {
            params = objectMapper.readValue(po.getExtraData(), Map.class);
        } catch (JsonProcessingException e) {
            throw new PersistenceException(e);
        }

        params.put("dsName", po.getDsName());
        return componentFactory.create(po.getName(), po.getScreenName(), po.getType(), params);
    }

    @Override
    public <T extends Component> Optional<T> getComponent(String componentName) {
        TypedQuery<FormDefinitionComponentPo> query = entityManager.createQuery("select c from FormDefinitionComponentPo c where c.formId = :formId and c.name = :itemName", FormDefinitionComponentPo.class);
        query.setParameter("formId", formDefinition.formId());
        query.setParameter("itemName", componentName);

        FormDefinitionComponentPo result = query.getSingleResult();
        if (result == null) {
            return Optional.empty();
        }

        Component component = toDomain(result);
        return (Optional<T>) Optional.of(component);
    }
}
