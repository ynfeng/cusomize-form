package com.github.ynfeng.customizeform.impl;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.ComponentFactory;
import com.github.ynfeng.customizeform.domain.Components;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.WithDataSource;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
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

        entityManager.persist(po);
    }

    @Override
    public List<Component> all() {
        TypedQuery<FormDefinitionComponentPo> query = entityManager.createQuery("select c from FormDefinitionComponentPo c where c.formId=:formId", FormDefinitionComponentPo.class);
        List<FormDefinitionComponentPo> pos = query.setParameter("formId", formDefinition.formId()).getResultList();
        return pos.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private Component toDomain(FormDefinitionComponentPo po) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("dsName", po.getDsName());

        return componentFactory.create(po.getName(), po.getScreenName(), po.getType(), params);
    }

    @Override
    public <T extends Component> Optional<T> getComponent(String componentName) {
        return Optional.empty();
    }
}
