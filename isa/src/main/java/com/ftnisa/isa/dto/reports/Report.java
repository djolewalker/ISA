package com.ftnisa.isa.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<DayStatistics> statisticsList;
    private int totalRides;
    private double avgRides;
    private float totalKilometers;
    private float avgKilometers;
    private int totalMoney;
    private double avgMoney;

}
