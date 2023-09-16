package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverResponse extends UserRequest {
    private boolean active;
    private boolean occupied;
    private String username;
    private String driverLicense;
    private VehicleResponse vehicle;
}
