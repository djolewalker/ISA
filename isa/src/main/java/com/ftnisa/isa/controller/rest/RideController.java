package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.mapper.RideMapper;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.service.RideService;
import com.ftnisa.isa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ride", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class RideController {
    private final RideService rideService;
    private final RideMapper rideMapper;

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking")
    @Transactional
    public ResponseEntity<RideDto> rideBooking(@RequestBody RideBookingRequestDto rideBookingRequestDTO) {
        try {
            var ride = rideService.bookARide(rideBookingRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rideMapper.rideToRideDto(ride));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/clone")
    public ResponseEntity<RideDto> recreateRide(@PathVariable int id, @RequestBody RecreateRideDto recreateRideDto) {
        try {
            var ride = rideService.recreateRide(id, recreateRideDto);
            var rideBookingResponseDto = rideMapper.rideToRideDto(ride);
            return ResponseEntity.status(HttpStatus.CREATED).body(rideBookingResponseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/accept")
    public ResponseEntity<Void> acceptOrRejectRideByPassenger(@PathVariable int id,
                                                              @RequestBody RideAcceptanceDto rideAcceptanceDto) {
        try {
            rideService.finalizeRideBooking(rideAcceptanceDto.isAccepted(), id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<Void> rejectRideByDriver(@PathVariable int id,
                                                   @RequestBody RideRejectionRequestDto rideRejectionRequestDto) {
        rideService.rejectRideByDriver(id, rideRejectionRequestDto.getReason());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/{id}/start")
    public ResponseEntity<Void> startRideByDriver(@PathVariable int id) {
        rideService.startRideByDriver(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/{id}/finish")
    public ResponseEntity<Void> finishRideByDriver(@PathVariable int id) {
        rideService.finishRideByDriver(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('DRIVER','USER')")
    @PostMapping("/{id}/panic")
    public ResponseEntity<Void> panic(@PathVariable int id, @RequestBody PanicRequestDto panicRequestDto,
                                      Principal principal) {
        var user = userService.findByUsername(principal.getName());
        var panic = rideService.panic(user, id, panicRequestDto.getReason());
        if (panic == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(@PathVariable Integer id) {
        var ride = rideService.findRideById(id);
        if (ride == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rideMapper.rideToRideDto(ride));
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @GetMapping("/ride-history")
    public ResponseEntity<List<RideDto>> rideHistory(@RequestParam Integer userId) {
        try {
            List<Ride> rides = rideService.getUsersWholeRideHistory(userId);
            return ResponseEntity.ok(rideMapper.ridesToRidesDto(rides));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping("/ride-history-by-date")
    public ResponseEntity<List<RideDto>> rideHistoryByDate(
            @RequestBody RideHistoryByDateRequestDto rideHistoryByDateRequestDto) {
        try {
            List<Ride> rides = rideService.getUsersRidesBetweenDates(
                    rideHistoryByDateRequestDto.getUserId(),
                    rideHistoryByDateRequestDto.getDate1(),
                    rideHistoryByDateRequestDto.getDate2());
            if (rides == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(rideMapper.ridesToRidesDto(rides));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PutMapping("/{id}/add-ride-to-favourites")
    public ResponseEntity<Void> rideHistoryByDate(@PathVariable int id) {
        try {
            rideService.addRideToFavourites(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
