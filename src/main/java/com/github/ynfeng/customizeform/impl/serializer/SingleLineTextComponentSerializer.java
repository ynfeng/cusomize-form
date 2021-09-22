package com.github.ynfeng.customizeform.impl.serializer;

import com.github.ynfeng.customizeform.domain.text.SingleLineText;

public class SingleLineTextComponentSerializer implements JsonComponentSerializer<SingleLineText> {

    @Override
    public String fromComponent(SingleLineText component) {
        return "";
    }

    @Override
    public SingleLineText toComponent(String name, String screenName, String json) {
        return new SingleLineText(name, screenName);
    }
}
