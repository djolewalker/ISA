package com.ftnisa.isa.controller.rest;


import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.mapper.LocationMapper;
import com.ftnisa.isa.mapper.RideMapper;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.repository.VehicleTypeRepository;
import com.ftnisa.isa.service.RideService;
import com.ftnisa.isa.service.RouteService;
import com.ftnisa.isa.service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/ride", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
public class RideController {

    private final RideService rideService;

    private final VehicleTypeRepository vehicleTypeRepository;

    private final RouteService routeService;

    private final RideMapper rideMapper;

    private final LocationMapper locationMapper;


    @Autowired
    public RideController(RideService rideService, VehicleTypeRepository vehicleTypeRepository, RouteService routeService, RideMapper rideMapper, LocationMapper locationMapper) {
        super();
        this.rideService = rideService;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.routeService = routeService;
        this.rideMapper = rideMapper;
        this.locationMapper = locationMapper;
    }




    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    public ResponseEntity<RideDto> rideBooking(@RequestBody RideBookingRequestDto rideBookingRequestDTO){
        try {
            var ride = rideService.bookARide(rideBookingRequestDTO);
            var rideBookingResponseDto = rideMapper.rideToRideDto(ride);
            return ResponseEntity.status(HttpStatus.CREATED).body(rideBookingResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/clone")
    public ResponseEntity<RideDto> recreateRide(@RequestBody RecreateRideDto recreateRideDto){
        try {
            Ride ride = rideService.recreateRide(recreateRideDto.getRideId());

            if (recreateRideDto.getScheduled()){
                ride.setStartTime(recreateRideDto.getScheduledStartTime());
                rideService.scheduledRideBooking(ride);
            } else {
                rideService.requestQuickRideBooking(ride);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(rideMapper.rideToRideDto(ride));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/acceptance")
    public ResponseEntity<Void> acceptOrRejectRideByPassenger(@RequestBody RideAcceptanceDto rideAcceptanceDto){
        try {
            rideService.finalizeRideBooking(rideAcceptanceDto.isRideAccepted(), rideAcceptanceDto.getRideId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/reject")
    public ResponseEntity<Void> rejectRideByDriver(@RequestBody RideRejectionRequestDto rideRejectionRequestDto){
        rideService.rejectRideByDriver(rideRejectionRequestDto.getRideId(), rideRejectionRequestDto.getRejectionReason());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/start")
    public ResponseEntity<Void> startRideByDriver(@RequestBody Integer rideId){
        rideService.startRideByDriver(rideId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/finish")
    public ResponseEntity<Void> finishRideByDriver(@RequestBody Integer rideId){
        rideService.finishRideByDriver(rideId);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping("/panic")
    public ResponseEntity<Void> panic(@RequestBody PanicRequestDto panicRequestDto){
        Panic panic = rideService.panic(panicRequestDto.getUserId(), panicRequestDto.getRideId(), panicRequestDto.getPanicReason());
        if (panic == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @GetMapping("/ride-history")
    public ResponseEntity<List<RideDto>> rideHistory(@RequestParam Integer userId){
        try {
            List<Ride> rides = rideService.getUsersWholeRideHistory(userId);
            List<RideDto> rideDtos = rides.stream().map(r -> rideMapper.rideToRideDto(r)).toList();
            return new ResponseEntity<>(rideDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping("/ride-history-by-date")
    public ResponseEntity<List<RideDto>> rideHistoryByDate(@RequestBody RideHistoryByDateRequestDto rideHistoryByDateRequestDto){
        try {
            List<Ride> rides = rideService.getUsersRidesBetweenDates(
                    rideHistoryByDateRequestDto.getUserId(),
                    rideHistoryByDateRequestDto.getDate1(),
                    rideHistoryByDateRequestDto.getDate2()
            );
            if (rides == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<RideDto> rideDtos = rides.stream().map(r -> rideMapper.rideToRideDto(r)).toList();
            return new ResponseEntity<>(rideDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PutMapping("/add-ride-to-favourites")
    public ResponseEntity<Void> rideHistoryByDate(@RequestParam Integer rideId){
        try {
            rideService.addRideToFavourites(rideId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }




















}
