package com.ftnisa.isa.controller.rest;


import com.ftnisa.isa.dto.reports.Report;
import com.ftnisa.isa.dto.ride.PanicRequestDto;
import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/admin-cumulative")
    public ResponseEntity<Report> cumulativeAdminReport(@RequestBody LocalDate startDate, LocalDate endDate) {
        try {
            Report report = reportService.cumulativeAdminReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
