package com.ftnisa.isa.dto.reports;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
