package com.ftnisa.isa.dto.user;


public class CreateDriverRequest extends DriverRequest {
    private String username;
    private String password;

    public CreateDriverRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
