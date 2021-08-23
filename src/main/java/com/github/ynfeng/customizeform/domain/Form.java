package com.github.ynfeng.customizeform.domain;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

public class Form {
    private String formId;
    private String name;
    private final List<Component> components = Lists.newArrayList();

    private Form(String formId) {
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
        return new FormBuilder(new Form(id));
    }

    public static class FormBuilder {
        private final Form form;

        public FormBuilder(Form form) {
            this.form = form;
        }

        public FormBuilder withName(String name) {
            this.form.name = name;
            return this;
        }

        public Form build() {
            return form;
        }
    }
}
