package com.barrycommins.refreshscopeproblem;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties
public class RefreshScopeConfigurationProperties {

    private String stringProperty;
    private List<String> listProperty;
}
