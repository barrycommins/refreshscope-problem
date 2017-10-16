package com.barrycommins.refreshscopeproblem;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@RefreshScope
@ConfigurationProperties
public class RefreshScopeConfigurationProperties {

    private String stringProperty;
    private List<String> listProperty;
}

