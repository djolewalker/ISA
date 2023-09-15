package com.ftnisa.isa.dto.vehicle;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleRequest extends VehicleDto {
    private int vehicleTypeId;
}
