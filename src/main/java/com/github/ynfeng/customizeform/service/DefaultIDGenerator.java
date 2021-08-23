package com.github.ynfeng.customizeform.service;

import java.util.UUID;

public class DefaultIDGenerator implements IDGenerator {
    @Override
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}
