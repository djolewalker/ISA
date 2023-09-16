package com.ftnisa.isa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AppConfiguration {
    @Value("${isa.app.url}")
    private String appUrl;

    public String getAppUrl() {
        return appUrl;
    }
}
