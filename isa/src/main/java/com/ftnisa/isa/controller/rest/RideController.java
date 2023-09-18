package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.dto.user.PanicResponse;
import com.ftnisa.isa.mapper.RideMapper;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.service.DriverService;
import com.ftnisa.isa.service.RideService;
import com.ftnisa.isa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ride", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class RideController {
    private final RideService rideService;
    private final RideMapper rideMapper;

    private final UserService userService;
    private final DriverService driverService;
    private final SimpMessagingTemplate template;

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
            var ride = rideService.finalizeRideBooking(rideAcceptanceDto.isAccepted(), id);
            if (rideAcceptanceDto.isAccepted()) {
                template.convertAndSendToUser(ride.getPassenger().getUsername(), "/queue/active-ride", id);
                template.convertAndSendToUser(ride.getDriver().getUsername(), "/queue/assigned-ride", id);
            }
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
        var ride = rideService.startRideByDriver(id);
        template.convertAndSendToUser(ride.getPassenger().getUsername(), "/queue/start-ride", ride.getId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('DRIVER')")
    @PutMapping("/{id}/finish")
    public ResponseEntity<Void> finishRideByDriver(@PathVariable int id) {
        var ride = rideService.finishRideByDriver(id);
        template.convertAndSendToUser(ride.getPassenger().getUsername(), "/queue/finish-ride", id);
        template.convertAndSendToUser(ride.getDriver().getUsername(), "/queue/finish-ride", id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('DRIVER','USER')")
    @PutMapping("/{id}/panic")
    public ResponseEntity<Void> panic(@PathVariable int id, @RequestBody PanicRequestDto panicRequestDto,
            Principal principal) {
        var user = userService.findByUsername(principal.getName());
        var panic = rideService.panic(user, id, panicRequestDto.getReason());
        if (panic == null) {
            return ResponseEntity.badRequest().build();
        }

        template.convertAndSendToUser(panic.getRide().getPassenger().getUsername(), "/queue/panic-car-resolved",
                panic.getRide().getDriver().getId());
        template.convertAndSendToUser(panic.getRide().getDriver().getUsername(), "/queue/panic-car-resolved",
                panic.getRide().getDriver().getId());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/panic/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PanicResponse> resolvePanic(@PathVariable Integer id) {
        try {
            var panic = userService.resolvePanic(id);
            if (panic == null) {
                return ResponseEntity.badRequest().build();
            }
            template.convertAndSendToUser(panic.getRide().getPassenger().getUsername(), "/queue/panic-car",
                    panic.getRide().getDriver().getId());
            template.convertAndSendToUser(panic.getRide().getDriver().getUsername(), "/queue/panic-car",
                    panic.getRide().getDriver().getId());

            return ResponseEntity.ok(rideMapper.panicToPanicResponse(panic));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

    @PreAuthorize("hasAnyRole('USER', 'DRIVER')")
    @Transactional
    @GetMapping("/ride-history")
    public ResponseEntity<List<RideDto>> rideHistory(Principal principal) {
        try {
            var user = userService.findByUsername(principal.getName());
            List<Ride> rides;
            if (user.hasRole(Role.DRIVER)) {
                var driver = driverService.findDriverById(user.getId());
                rides = rideService.getDriversWholeRideHistory(driver);
            } else {
                rides = rideService.getUsersWholeRideHistory(user);
            }
            return ResponseEntity.ok(rideMapper.ridesToRidesDto(rides));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @GetMapping("/ride-history/{id}")
    public ResponseEntity<List<RideDto>> accountRideHistory(@PathVariable Integer id) {
        try {
            var user = userService.findById(id);
            List<Ride> rides = new ArrayList<Ride>();
            if (user.hasRole(Role.DRIVER)) {
                var driver = driverService.findDriverById(user.getId());
                rides = rideService.getDriversWholeRideHistory(driver);
            } else if (user.hasRole(Role.USER)) {
                rides = rideService.getUsersWholeRideHistory(user);
            }
            return ResponseEntity.ok(rideMapper.ridesToRidesDto(rides));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PostMapping("/ride-history-by-date")
    public ResponseEntity<List<RideDto>> rideHistoryByDate(
            @RequestBody RideHistoryByDateRequestDto rideHistoryByDateRequestDto, Principal principal) {
        try {
            var user = userService.findByUsername(principal.getName());
            List<Ride> rides = rideService.getUsersRidesBetweenDates(
                    user,
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
    @PutMapping("/{id}/favourite/add")
    public ResponseEntity<Void> makeRideFavourite(@PathVariable int id) {
        try {
            rideService.addRideToFavourites(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @PutMapping("/{id}/favourite/remove")
    public ResponseEntity<Void> removeFromFavourite(@PathVariable int id) {
        try {
            rideService.removeFromFavourites(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
