package com.github.ynfeng.customizeform.config;

import com.github.ynfeng.customizeform.domain.repository.FormRepository;
import com.github.ynfeng.customizeform.domain.repository.impl.MemoryFormRepository;
import com.github.ynfeng.customizeform.service.DefaultIDGenerator;
import com.github.ynfeng.customizeform.service.IDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    FormRepository formRepository() {
        return new MemoryFormRepository();
    }

    @Bean
    IDGenerator idGenerator() {
        return new DefaultIDGenerator();
    }
}
