package com.barrycommins.refreshscopeproblem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RefreshScopeConfigurationProperties.class)
@SpringBootApplication
public class RefreshScopeProblemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefreshScopeProblemApplication.class, args);
    }
}
