package com.ftnisa.isa.dto.reports;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayStatistics {

    private LocalDate date;
    private int numOfRides;
    private float numOfKilometers;
    private int money;

}
