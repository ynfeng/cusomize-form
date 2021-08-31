package com.github.ynfeng.customizeform.service;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.BusinessComponentFactory;
import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CreateFormDefinitionService {
    private static final Map<String, Object> EMPTY_PARAMS = Maps.newHashMap();
    private final FormDefinitionRepository formDefinitionRepository;
    private final IDGenerator idGenerator;
    private final BusinessComponentFactory componentFactory;

    public CreateFormDefinitionService(FormDefinitionRepository formDefinitionRepository,
                                       DatasourceFactory datasourceFactory,
                                       IDGenerator idGenerator) {
        componentFactory = new BusinessComponentFactory(datasourceFactory);
        this.formDefinitionRepository = formDefinitionRepository;
        this.idGenerator = idGenerator;
    }

    public String create(CreateFormDefinitionRequest request) {
        FormDefinition formDefinition = FormDefinition
            .withId(idGenerator.nextId())
            .withName(request.getName())
            .build();

        request.getItems().forEach(formItem -> {
            Component formComponent = componentFactory.create(formItem.getName(), formItem.getScreenName(), formItem.getType(), formItem.getParams());
            formDefinition.addItem(formComponent);
        });

        return formDefinitionRepository.save(formDefinition).formId();
    }
}
