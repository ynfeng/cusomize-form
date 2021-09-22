package com.github.ynfeng.customizeform.impl;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.po.FormDefinitionPo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class DBFormDefinitionRepository implements FormDefinitionRepository {
    private final EntityManager entityManager;
    private final DatasourceFactory datasourceFactory;

    public DBFormDefinitionRepository(EntityManager entityManager, DatasourceFactory datasourceFactory) {
        this.entityManager = entityManager;
        this.datasourceFactory = datasourceFactory;
    }

    @Override
    public FormDefinition save(FormDefinition formDefinition) {
        FormDefinitionPo po = FormDefinitionPo.fromDomain(formDefinition);
        entityManager.persist(po);
        return formDefinition;
    }

    @Override
    public Optional<FormDefinition> find(String formId) {
        TypedQuery<FormDefinitionPo> query = entityManager.createQuery("select fd from FormDefinitionPo  as fd where fd.formId = :formId", FormDefinitionPo.class);
        FormDefinitionPo po = query.setParameter("formId", formId).getSingleResult();

        if (po == null) {
            Optional.empty();
        }

        return Optional.of(toDomain(po));
    }

    @Override
    public List<FormDefinition> all() {
        List<FormDefinitionPo> poList = entityManager.createQuery("select fd from FormDefinitionPo as fd").getResultList();
        return poList.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private FormDefinition toDomain(FormDefinitionPo formDefinitionPo) {
        FormDefinition result = FormDefinition
            .withId(formDefinitionPo.getFormId())
            .withName(formDefinitionPo.getName())
            .build();

        result.setComponents(new DBComponents(result, entityManager, datasourceFactory));
        return result;
    }
}
