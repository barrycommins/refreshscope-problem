package com.barrycommins.refreshscopeproblem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RefreshScopeController {

    private final RefreshScopeConfigurationProperties properties;

    public RefreshScopeController(RefreshScopeConfigurationProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/string")
    public String readStringProperty() {
        return properties.getStringProperty();
    }

    @GetMapping("/list")
    public List<String> readListProperty() {
        return properties.getListProperty();
    }
}
