package com.ftnisa.isa.dto.reports;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualAdminReportRequest extends ReportRequest{
    private Integer userId;
}
