package com.ftnisa.isa.controller.rest;


import com.ftnisa.isa.dto.vehicle.VehicleRequest;
import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;
import com.ftnisa.isa.mapper.VehicleMapper;
import com.ftnisa.isa.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createVehicle(@RequestBody VehicleRequest vehicleDto){
        try {
            vehicleService.createVehicle(vehicleDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/types")
    public ResponseEntity<List<VehicleTypeResponse>> getVehicleTypes() {
        var vehicleTypes = vehicleService.getVehicleTypes();
        return ResponseEntity.ok(
                vehicleTypes
                        .stream()
                        .map(vehicleMapper::vehicleTypeToVehicleTypeResponse)
                        .toList()
        );
    }
}
