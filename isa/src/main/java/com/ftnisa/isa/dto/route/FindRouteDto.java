package com.ftnisa.isa.dto.route;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class FindRouteDto {
    @NotEmpty(message = "Steps are required!")
    @Size(min = 2, max = 10)
    private Double[][] stops;
}
