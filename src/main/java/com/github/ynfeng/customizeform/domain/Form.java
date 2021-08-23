package com.github.ynfeng.customizeform.domain;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

public class Form {
    private final String formId;
    private final String name;
    private final List<Component> components = Lists.newArrayList();

    public Form(String formId, String name) {
        this.formId = formId;
        this.name = name;
    }

    public void addItem(Component component) {
        components.add(component);
    }

    public <T extends Component> Optional<T> getItem(String itemName) {
        return (Optional<T>) components.stream()
            .filter(it -> it.name().equals(itemName))
            .findAny();
    }

    public String formId() {
        return formId;
    }
}
