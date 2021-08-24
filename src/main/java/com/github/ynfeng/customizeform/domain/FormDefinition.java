package com.github.ynfeng.customizeform.domain;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FormDefinition {
    private String formId;
    private String name;
    private final List<Component> components = Lists.newArrayList();

    private FormDefinition(String formId) {
        this.formId = formId;
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

    public static FormBuilder withId(String id) {
        return new FormBuilder(new FormDefinition(id));
    }

    public String name() {
        return name;
    }

    public List<Component> items() {
        return Collections.unmodifiableList(components);
    }

    public static class FormBuilder {
        private final FormDefinition formDefinition;

        public FormBuilder(FormDefinition formDefinition) {
            this.formDefinition = formDefinition;
        }

        public FormBuilder withName(String name) {
            this.formDefinition.name = name;
            return this;
        }

        public FormDefinition build() {
            return formDefinition;
        }
    }
}
