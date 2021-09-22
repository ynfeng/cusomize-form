package com.github.ynfeng.customizeform.domain.repository.impl;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class DBFormDefinitionRepository implements FormDefinitionRepository {

    private final EntityManager entityManager;

    public DBFormDefinitionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public FormDefinition save(FormDefinition formDefinition) {
        FormDefinitionPo po = FormDefinitionPo.fromDomain(formDefinition);
        entityManager.persist(po);
        return formDefinition;
    }

    @Override
    public Optional<FormDefinition> find(String formId) {
        return Optional.empty();
    }

    @Override
    public List<FormDefinition> all() {
        return null;
    }
}
