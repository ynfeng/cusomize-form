package com.github.ynfeng.customizeform.domain;

import java.util.List;
import java.util.Optional;
import lombok.Setter;

public class FormDefinition {
    private final String formId;
    private String name;
    @Setter
    private Components components = new DefaultComponents();

    private FormDefinition(String formId) {
        this.formId = formId;
    }

    public void addItem(Component component) {
        components.add(component);
    }

    public <T extends Component> Optional<T> getItem(String itemName) {
        return components.getComponent(itemName);
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
        return components.all();
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
