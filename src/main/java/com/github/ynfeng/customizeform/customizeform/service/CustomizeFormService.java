package com.github.ynfeng.customizeform.customizeform.service;

import com.github.ynfeng.customizeform.customizeform.domain.Component;
import com.github.ynfeng.customizeform.customizeform.domain.Form;
import com.github.ynfeng.customizeform.customizeform.domain.business.BusinessComponentFactory;
import com.github.ynfeng.customizeform.customizeform.domain.repository.FormRepository;

public class CustomizeFormService {
    private final FormRepository formRepository;
    private final IDGenerator idGenerator;
    private final BusinessComponentFactory componentFactory = new BusinessComponentFactory();

    public CustomizeFormService(FormRepository formRepository,
                                IDGenerator idGenerator) {
        this.formRepository = formRepository;
        this.idGenerator = idGenerator;
    }

    public String create(CreateFormRequest request) {
        Form form = new Form(idGenerator.nextId(), request.getName());
        String companyId = request.getCompanyId();

        request.getItems().forEach(formItem -> {
            Component formComponent = componentFactory.create(formItem.getName(), formItem.getScreenName(), companyId, formItem.getType());
            form.addItem(formComponent);
        });

        return formRepository.save(form).formId();
    }
}
