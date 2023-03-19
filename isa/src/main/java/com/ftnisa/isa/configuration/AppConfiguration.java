package com.ftnisa.isa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {
    @Value("${spring.app.url}")
    private String appUrl;

    public String getAppUrl() {
        return appUrl;
    }
}
