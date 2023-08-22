package com.ftnisa.isa.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDriverRequest extends DriverRequest {
    private String username;
    private String password;
}
