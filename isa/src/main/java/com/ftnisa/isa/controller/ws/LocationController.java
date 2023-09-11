package com.ftnisa.isa.controller.ws;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.dto.user.DriverLocationDto;
import com.ftnisa.isa.mapper.UserMapper;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.service.DriverService;
import com.ftnisa.isa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class LocationController {
    private UserService userService;
    private DriverService driverService;
    private UserMapper mapper;

    @MessageMapping("/driver/location")
    public DriverLocationDto updateDriverLocation(LocationDto location, Principal principal) {
        var user = userService.findByUsername(principal.getName());
        if (!user.hasRole(Role.DRIVER)){
            return null;
        }

        var driver = driverService.updateDriverLocation(user.getId(), location.getLongitude(), location.getLatitude());
        return mapper.driverToDriverLocationDto(driver);
    }
}
