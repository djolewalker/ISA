package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverInfoDto {
    private String firstname;
    private String lastname;
    private String image;
    private VehicleResponse vehicle;
}
