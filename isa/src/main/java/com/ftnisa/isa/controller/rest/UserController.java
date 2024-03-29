package com.ftnisa.isa.controller.rest;

import java.security.Principal;
import java.util.List;

import com.ftnisa.isa.dto.user.*;
import com.ftnisa.isa.mapper.UserMapper;
import com.ftnisa.isa.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.ftnisa.isa.service.UserService;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final DriverService driverService;
    private final UserMapper mapper;
    private SimpMessagingTemplate template;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> all() {
        var users = this.userService.findAll();
        var usersResponse = users.stream()
                .map(mapper::toUserResponse)
                .toList();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> info(Principal user) {
        var foundUser = this.userService.findByUsername(user.getName());
        return ResponseEntity.ok(mapper.toUserResponse(foundUser));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        var foundUser = this.userService.findById(id);
        return ResponseEntity.ok(mapper.toUserResponse(foundUser));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponse> updateUserProfile(Principal principal, @RequestBody UserRequest userRequest) {
        var user = this.userService.updateUserProfile(principal.getName(), userRequest);
        return ResponseEntity.ok(mapper.toUserResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        var user = this.userService.updateUser(id, userRequest);
        return ResponseEntity.ok(mapper.toUserResponse(user));
    }

    @PostMapping("/driver-change-request")
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<Void> createDriverChangeRequest(@RequestBody DriverChangeRequestDto driverChangeRequestDto) {
        try {
            this.userService.createDriverChangeRequest(driverChangeRequestDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/driver-change-resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DriverResponse> resolveDriverChangeRequest(
            @RequestBody DriverChangeResolveDto driverChangeResolveDto) {
        try {
            var driver = userService.resolveDriverChangeRequest(
                    driverChangeResolveDto.getDriverChangeId(),
                    driverChangeResolveDto.isApproved());
            return ResponseEntity.ok(mapper.driverToDriverResponse(driver));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/driver")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DriverResponse> createDriver(@RequestBody CreateDriverRequest driverRequest) {
        var driver = userService.registerDriver(driverRequest);
        return ResponseEntity.ok(mapper.driverToDriverResponse(driver));
    }

    @GetMapping("/driver/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable Integer id) {
        var foundDriver = this.driverService.findDriverById(id);
        return ResponseEntity.ok(mapper.driverToDriverResponse(foundDriver));
    }

    @GetMapping("/driver/location")
    public ResponseEntity<List<DriverLocationDto>> getDriversLocation() {
        var drivers = this.driverService.getActiveDrivers();
        return ResponseEntity.ok(mapper.driversToDriversLocationDto(drivers));
    }

    @PutMapping("/driver/deactivate")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Void> deactivateDriver(Principal principal) {
        var driver = this.driverService.deactivateDriver(principal.getName());
        template.convertAndSend("/topic/driver/deactivated", driver.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/driver/activate")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Void> activateDriver(Principal principal) {
        var driver = this.driverService.activateDriver(principal.getName());
        template.convertAndSend("/topic/driver/activated", driver.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/driver/status")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<DriverStatusDTO> isDriverActive(Principal principal) {
        var driver = this.driverService.activateDriver(principal.getName());
        return ResponseEntity.ok(mapper.driverToDriverStatusDto(driver));
    }
}
