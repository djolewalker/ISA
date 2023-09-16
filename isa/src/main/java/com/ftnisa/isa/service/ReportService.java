package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.reports.Report;

import java.time.LocalDate;

public interface ReportService {


    Report getIndividualAdminReport(LocalDate startDate, LocalDate endDate, Integer userId);

    Report getReport(LocalDate startDate, LocalDate endDate);

}
