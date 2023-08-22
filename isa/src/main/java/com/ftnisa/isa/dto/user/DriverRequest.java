package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DriverRequest extends UserRequest {
    private String driverLicense;
    private VehicleRequest vehicle;
}
