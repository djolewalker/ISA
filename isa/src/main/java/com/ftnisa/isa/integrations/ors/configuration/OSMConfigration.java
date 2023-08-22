package com.ftnisa.isa.integrations.ors.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSMConfigration {

    @Value("${isa.ors.url}")
    private String osmUrl;

    @Value("${isa.ors.token}")
    private String osmToken;

    public String getOsmUrl() {
        return osmUrl;
    }

    public String getOsmToken() {
        return osmToken;
    }
}
