package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverResponse extends UserRequest {
    private boolean active;
    private boolean occupied;
    private String username;
    private String driverLicense;
    private VehicleResponse vehicle;
}
