package com.github.ynfeng.customizeform.service;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.ComponentFactory;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.DBComponents;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateFormDefinitionService {
    private final FormDefinitionRepository formDefinitionRepository;
    private final IDGenerator idGenerator;
    private final ComponentFactory componentFactory;
    private final EntityManager entityManager;
    private final DatasourceFactory datasourceFactory;

    public CreateFormDefinitionService(FormDefinitionRepository formDefinitionRepository,
                                       DatasourceFactory datasourceFactory,
                                       IDGenerator idGenerator,
                                       EntityManager entityManager) {
        componentFactory = new ComponentFactory(datasourceFactory);
        this.formDefinitionRepository = formDefinitionRepository;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.datasourceFactory = datasourceFactory;
    }

    public String create(CreateFormDefinitionRequest request) {
        FormDefinition formDefinition = FormDefinition
            .withId(idGenerator.nextId())
            .withName(request.getName())
            .build();

        formDefinition.setComponents(new DBComponents(formDefinition, entityManager, datasourceFactory));

        request.getItems().forEach(formItem -> {
            Component formComponent = componentFactory.create(formItem.getName(), formItem.getScreenName(), formItem.getType(), formItem.getParams());
            formDefinition.addItem(formComponent);
        });

        return formDefinitionRepository.save(formDefinition).formId();
    }
}
