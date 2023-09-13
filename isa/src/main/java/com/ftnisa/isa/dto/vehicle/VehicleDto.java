package com.ftnisa.isa.dto.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDto {
    protected String vehicleModel;
    protected String registrationNumber;
    protected int numberOfSeats;
    protected boolean babyFriendly;
    protected boolean petFriendly;
}
