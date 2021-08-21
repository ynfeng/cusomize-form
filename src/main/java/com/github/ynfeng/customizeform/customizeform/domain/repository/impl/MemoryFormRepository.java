package com.github.ynfeng.customizeform.customizeform.domain.repository.impl;

import com.github.ynfeng.customizeform.customizeform.domain.Form;
import com.github.ynfeng.customizeform.customizeform.domain.repository.FormRepository;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

public class MemoryFormRepository implements FormRepository {
    private final List<Form> memory = Lists.newArrayList();

    @Override
    public Form save(Form form) {
        memory.add(form);
        return form;
    }

    @Override
    public Optional<Form> find(String formId) {
        return memory.stream().filter(it -> it.formId().equals(formId)).findAny();
    }
}
