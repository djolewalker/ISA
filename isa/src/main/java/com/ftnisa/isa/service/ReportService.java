package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.reports.Report;

import java.time.LocalDate;

public interface ReportService {
    Report cumulativeAdminReport(LocalDate startDate, LocalDate endDate);
}
