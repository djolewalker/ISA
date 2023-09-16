package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.reports.Report;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public Report cumulativeAdminReport(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
