package com.ftnisa.isa.dto.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleRequest extends VehicleDto {
    private int vehicleTypeId;
}
