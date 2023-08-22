package com.ftnisa.isa.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String email;
    private String firstname;
    private String lastname;
    private String image;
    private String phone;
    private String address;
}
