package com.github.ynfeng.customizeform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CustomizeFormApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomizeFormApplication.class, args);
    }

}
