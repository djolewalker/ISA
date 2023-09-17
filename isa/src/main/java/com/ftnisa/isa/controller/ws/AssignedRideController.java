package com.ftnisa.isa.controller.ws;

import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.service.DriverService;
import com.ftnisa.isa.service.RideService;
import com.ftnisa.isa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class AssignedRideController {
    private DriverService driverService;
    private UserService userService;
    private RideService rideService;

    @MessageMapping("/assigned-ride")
    @SendToUser("/queue/assigned-ride")
    public Integer getRideAssignedToUser(Principal principal) {
        var driver = driverService.findDriverByUsername(principal.getName());
        if (driver == null) return null;

        var ride = rideService.findDriversActiveRide(driver);
        if (ride == null) return null;
        return ride.getId();
    }

    @MessageMapping("/active-ride")
    @SendToUser("/queue/active-ride")
    public Integer getUsersActiveRideRide(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        if (!user.hasRole(Role.USER)) return null;

        var ride = rideService.findUsersActiveRide(user);
        if (ride == null) return null;
        return ride.getId();
    }
}
