package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.notification.UserNotificationDto;
import com.ftnisa.isa.mapper.NotificationMapper;
import com.ftnisa.isa.service.NotificationService;
import com.ftnisa.isa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/notification", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
@AllArgsConstructor
public class NotificationController {
    private NotificationService notificationService;
    private UserService userService;
    private NotificationMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserNotificationDto>> getNotifications(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        var userNotifications = notificationService.getUserNotifications(user);
        return ResponseEntity.ok(mapper.userNotificationsToUserNotificationsDto(userNotifications));
    }
}
