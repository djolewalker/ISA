package com.ftnisa.isa.dto.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleResponse extends VehicleDto {
    private int id;
    private VehicleTypeResponse vehicleType;
}
