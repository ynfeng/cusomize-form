package com.github.ynfeng.customizeform.service;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.business.BusinessComponentFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CreteFormDefinitionService {
    private final FormDefinitionRepository formDefinitionRepository;
    private final IDGenerator idGenerator;
    private final BusinessComponentFactory componentFactory = new BusinessComponentFactory();
    private static final Map<String, Object> EMPTY_PARAMS = Maps.newHashMap();

    public CreteFormDefinitionService(FormDefinitionRepository formDefinitionRepository,
                                      IDGenerator idGenerator) {
        this.formDefinitionRepository = formDefinitionRepository;
        this.idGenerator = idGenerator;
    }

    public String create(CreateFormDefinitionRequest request) {
        FormDefinition formDefinition = FormDefinition
            .withId(idGenerator.nextId())
            .withName(request.getName())
            .build();

        request.getItems().forEach(formItem -> {
            Component formComponent = componentFactory.create(formItem.getName(), formItem.getScreenName(), formItem.getType(), EMPTY_PARAMS);
            formDefinition.addItem(formComponent);
        });

        return formDefinitionRepository.save(formDefinition).formId();
    }
}
