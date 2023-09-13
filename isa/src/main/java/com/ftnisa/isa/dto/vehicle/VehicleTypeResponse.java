package com.ftnisa.isa.dto.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleTypeResponse {
    private int id;
    private String vehicleTypeName;
    private Float pricePerKm;
}
