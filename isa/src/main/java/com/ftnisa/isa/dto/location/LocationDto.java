package com.ftnisa.isa.dto.location;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private Integer id;
    private float longitude;
    private float latitude;
    private String name;
}
