package com.ftnisa.isa.dto.auth;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;
    private Long expiresIn;

    public JwtResponse() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public JwtResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
