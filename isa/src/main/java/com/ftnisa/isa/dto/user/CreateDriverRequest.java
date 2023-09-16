package com.ftnisa.isa.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDriverRequest extends DriverRequest {
    private String username;
    private String password;
}
