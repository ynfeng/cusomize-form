package com.github.ynfeng.customizeform.impl.serializer;

import com.github.ynfeng.customizeform.domain.Component;

public interface
JsonComponentSerializer<T extends Component> {
    String fromComponent(T component);

    T toComponent(String name, String screenName, String json);
}
