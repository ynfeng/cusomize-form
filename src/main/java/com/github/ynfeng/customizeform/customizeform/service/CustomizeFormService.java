package com.github.ynfeng.customizeform.customizeform.service;

import com.github.ynfeng.customizeform.customizeform.domain.Component;
import com.github.ynfeng.customizeform.customizeform.domain.Form;
import com.github.ynfeng.customizeform.customizeform.domain.business.BusinessComponentFactory;
import com.github.ynfeng.customizeform.customizeform.domain.repository.FormRepository;
import com.google.common.collect.Maps;
import java.util.Map;

public class CustomizeFormService {
    private final FormRepository formRepository;
    private final IDGenerator idGenerator;
    private final BusinessComponentFactory componentFactory = new BusinessComponentFactory();
    private static final Map<String, Object> EMPTY_PARAMS = Maps.newHashMap();

    public CustomizeFormService(FormRepository formRepository,
                                IDGenerator idGenerator) {
        this.formRepository = formRepository;
        this.idGenerator = idGenerator;
    }

    public String create(CreateFormRequest request) {
        Form form = new Form(idGenerator.nextId(), request.getName());

        request.getItems().forEach(formItem -> {
            Component formComponent = componentFactory.create(formItem.getName(), formItem.getScreenName(), formItem.getType(), EMPTY_PARAMS);
            form.addItem(formComponent);
        });

        return formRepository.save(form).formId();
    }
}
