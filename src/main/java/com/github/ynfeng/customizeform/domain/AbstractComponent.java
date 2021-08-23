package com.github.ynfeng.customizeform.domain;

public abstract class AbstractComponent implements Component {
    private final String name;
    private final String screenName;

    protected AbstractComponent(String name, String screenName) {
        this.name = name;
        this.screenName = screenName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String screenName() {
        return screenName;
    }
}
