package com.ftnisa.isa.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordRequest {
    private String email;
}
