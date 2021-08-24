package com.github.ynfeng.customizeform.service;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultIDGenerator implements IDGenerator {
    private final AtomicInteger id = new AtomicInteger();

    @Override
    public String nextId() {
        return String.valueOf(id.incrementAndGet());
    }
}
