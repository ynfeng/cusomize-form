package com.github.ynfeng.customizeform.domain.repository;

import com.github.ynfeng.customizeform.domain.FormDefinition;
import java.util.List;
import java.util.Optional;

public interface FormDefinitionRepository {
    FormDefinition save(FormDefinition formDefinition);

    Optional<FormDefinition> find(String formId);

    List<FormDefinition> all();
}
