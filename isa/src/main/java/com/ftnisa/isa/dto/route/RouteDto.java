package com.ftnisa.isa.dto.route;

import com.ftnisa.isa.dto.location.LocationDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteDto {
    private int id;
    private LocationDto startLocation;
    private LocationDto finishLocation;
}
