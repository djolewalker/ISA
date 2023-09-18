package com.ftnisa.isa.controller.rest;


import com.ftnisa.isa.dto.reports.IndividualAdminReportRequest;
import com.ftnisa.isa.dto.reports.Report;
import com.ftnisa.isa.dto.reports.ReportRequest;
import com.ftnisa.isa.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/individual")
    public ResponseEntity<Report> getIndividualAdminReport(@RequestBody IndividualAdminReportRequest individualAdminReportRequest) {
        try {
            Report report = reportService.getIndividualAdminReport(
                    individualAdminReportRequest.getStartDate(),
                    individualAdminReportRequest.getEndDate(),
                    individualAdminReportRequest.getUserId()
            );
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasAnyRole('DRIVER','USER','ADMIN')")
    @PostMapping()
    public ResponseEntity<Report> getReport(@RequestBody ReportRequest reportRequest) {
        try {
            Report report = reportService.getReport(reportRequest.getStartDate(), reportRequest.getEndDate());
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
