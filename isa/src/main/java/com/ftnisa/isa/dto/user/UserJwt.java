package com.ftnisa.isa.dto.user;

public class UserJwt {
    private String accessToken;
    private Long expiresIn;

    public UserJwt() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserJwt(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
