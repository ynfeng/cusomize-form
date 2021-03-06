package com.github.ynfeng.customizeform.impl;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryFormDefinitionRepository implements FormDefinitionRepository {
    private final List<FormDefinition> memory = Lists.newArrayList();

    @Override
    public FormDefinition save(FormDefinition formDefinition) {
        memory.add(formDefinition);
        return formDefinition;
    }

    @Override
    public Optional<FormDefinition> find(String formId) {
        return memory.stream().filter(it -> it.formId().equals(formId)).findAny();
    }

    @Override
    public List<FormDefinition> all() {
        return Collections.unmodifiableList(memory);
    }

    public void clear() {
        memory.clear();
    }
}
