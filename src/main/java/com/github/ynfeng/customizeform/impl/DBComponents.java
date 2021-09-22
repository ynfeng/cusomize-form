package com.github.ynfeng.customizeform.impl;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.Components;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.impl.po.FormDefinitionComponentPo;
import com.github.ynfeng.customizeform.impl.serializer.JsonComponentSerializer;
import com.github.ynfeng.customizeform.impl.serializer.JsonComponentSerializerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class DBComponents implements Components {
    private final EntityManager entityManager;
    private final FormDefinition formDefinition;

    public DBComponents(FormDefinition formDefinition, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.formDefinition = formDefinition;
    }

    @Override
    public void add(Component component) {
        JsonComponentSerializer<Component> serializer = JsonComponentSerializerFactory.get(component);

        FormDefinitionComponentPo po = new FormDefinitionComponentPo();
        po.setName(component.name());
        po.setScreenName(component.screenName());
        po.setData(serializer.fromComponent(component));
        po.setFormId(formDefinition.formId());
        po.setCreateTime(LocalDateTime.now());

        entityManager.persist(po);
    }

    @Override
    public List<Component> all() {
        return null;
    }

    @Override
    public <T extends Component> Optional<T> getComponent(String componentName) {
        return Optional.empty();
    }
}
