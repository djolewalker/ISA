package com.ftnisa.isa.controller;


import com.ftnisa.isa.dto.vehicle.VehicleDto;
import com.ftnisa.isa.mapper.LocationMapper;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.vehicle.Vehicle;
import com.ftnisa.isa.model.vehicle.VehicleType;
import com.ftnisa.isa.model.vehicle.VehicleTypeName;
import com.ftnisa.isa.repository.UserRepository;
import com.ftnisa.isa.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
public class VehicleController {


    private final VehicleService vehicleService;

    private final UserRepository userRepository;

    private final LocationMapper locationMapper;


    public VehicleController(VehicleService vehicleService, UserRepository userRepository, LocationMapper locationMapper) {
        this.vehicleService = vehicleService;
        this.userRepository = userRepository;
        this.locationMapper = locationMapper;
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/create-vehicle")
    public ResponseEntity<Void> createVehicle(@RequestBody VehicleDto vehicleDto){
        try {
            VehicleType vehicleType = new VehicleType(
                    VehicleTypeName.valueOf(vehicleDto.getVehicleType().getVehicleTypeName().toString()),
                    vehicleDto.getVehicleType().getPricePerKm()
            );

            Vehicle vehicle = new Vehicle(
                    vehicleDto.getVehicleModel(),
                    vehicleDto.getRegistrationNumber(),
                    vehicleDto.getNumberOfSeats(),
                    vehicleDto.isBabyFriendly(),
                    vehicleDto.isPetFriendly(),
                    userRepository.findById(vehicleDto.getUserId()).orElse(null),
                    vehicleType,
                    locationMapper.locationDtoToLocation(vehicleDto.getCurrentLocation())
            );
            vehicleService.saveNewVehicle(vehicle);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
