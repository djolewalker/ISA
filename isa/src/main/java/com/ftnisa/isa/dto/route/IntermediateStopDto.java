package com.ftnisa.isa.dto.route;

import com.ftnisa.isa.dto.location.LocationDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IntermediateStopDto {
    private int id;
    private LocationDto location;
    private int order;
}
