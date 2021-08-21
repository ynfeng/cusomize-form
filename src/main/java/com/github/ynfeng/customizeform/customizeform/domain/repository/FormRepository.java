package com.github.ynfeng.customizeform.customizeform.domain.repository;

import com.github.ynfeng.customizeform.customizeform.domain.Form;
import java.util.Optional;

public interface FormRepository {
    Form save(Form form);

    Optional<Form> find(String formId);
}
