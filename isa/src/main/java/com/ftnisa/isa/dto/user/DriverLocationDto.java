package com.ftnisa.isa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDto {
    private int id;
    private boolean occupied;
    private float longitude;
    private float latitude;
}
