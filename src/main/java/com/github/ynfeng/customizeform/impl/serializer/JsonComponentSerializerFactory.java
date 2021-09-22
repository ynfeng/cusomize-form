package com.github.ynfeng.customizeform.impl.serializer;

import com.github.ynfeng.customizeform.domain.Component;
import com.github.ynfeng.customizeform.domain.text.SingleLineText;

public class JsonComponentSerializerFactory {

    public static <T extends Component> JsonComponentSerializer<T> get(Component component) {
        if (component instanceof SingleLineText) {
            return (JsonComponentSerializer<T>) new SingleLineTextComponentSerializer();
        }

        throw new IllegalArgumentException("Not supported json component serializer");
    }
}
