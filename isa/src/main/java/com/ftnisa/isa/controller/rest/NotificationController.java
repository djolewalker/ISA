package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.notification.AdminNotificationDto;
import com.ftnisa.isa.dto.notification.UserNotificationDto;
import com.ftnisa.isa.mapper.NotificationMapper;
import com.ftnisa.isa.service.NotificationService;
import com.ftnisa.isa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
    @PreAuthorize("hasAnyRole('USER', 'DRIVER')")
    public ResponseEntity<List<UserNotificationDto>> getUserNotifications(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        var userNotifications = notificationService.getUserNotifications(user);
        return ResponseEntity.ok(mapper.userNotificationsToUserNotificationsDto(userNotifications));
    }

    @Transactional
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminNotificationDto>> getAdminNotifications() {
        var adminNotifications = notificationService.getAdminNotifications();
        return ResponseEntity.ok(mapper.adminNotificationsToAdminNotificationsDto(adminNotifications));
    }
}
