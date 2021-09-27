package com.github.ynfeng.customizeform.config;

import com.github.ynfeng.customizeform.domain.datasource.DatasourceFactory;
import com.github.ynfeng.customizeform.domain.datasource.SPIDatasourceFactory;
import com.github.ynfeng.customizeform.domain.repository.FormDefinitionRepository;
import com.github.ynfeng.customizeform.impl.DBFormDefinitionRepository;
import com.github.ynfeng.customizeform.service.IDGenerator;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    FormDefinitionRepository formRepository(EntityManager entityManager, DatasourceFactory datasourceFactory) {
        return new DBFormDefinitionRepository(entityManager, datasourceFactory);
    }

    @Bean
    IDGenerator idGenerator() {
        return () -> UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Bean
    DatasourceFactory datasourceFactory() {
        return new SPIDatasourceFactory();
    }
}
