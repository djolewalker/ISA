package com.ftnisa.isa.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String image;
    private String phone;
    private String address;
    private List<String> roles;
}
