package com.github.ynfeng.customizeform.config;

import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.DefaultDataSourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.InMemoryFormDefinitionRepository;
import com.github.ynfeng.customizeform.service.DefaultIDGenerator;
import com.github.ynfeng.customizeform.service.IDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    FormDefinitionRepository formRepository() {
        return new InMemoryFormDefinitionRepository();
    }

    @Bean
    IDGenerator idGenerator() {
        return new DefaultIDGenerator();
    }

    @Bean
    DatasourceFactory datasourceFactory() {
        return new DefaultDataSourceFactory();
    }
}
