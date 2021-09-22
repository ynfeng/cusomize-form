package com.github.ynfeng.customizeform.domain;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultComponents implements Components {
    private final List<Component> components = Lists.newArrayList();

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public List<Component> all() {
        return Collections.unmodifiableList(components);
    }

    @Override
    public <T extends Component> Optional<T> getComponent(String componentName) {
        return (Optional<T>) components.stream()
            .filter(it -> it.name().equals(componentName))
            .findAny();
    }
}
