package com.github.ynfeng.customizeform.domain;

import java.util.List;
import java.util.Optional;

public interface Components {
    void add(Component component);

    List<Component> all();

    <T extends Component> Optional<T> getComponent(String componentName);
}
