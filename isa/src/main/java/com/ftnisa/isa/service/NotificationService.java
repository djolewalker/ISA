package com.ftnisa.isa.service;

import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void createInstantNotification(User user, String message);

    void createScheduledNotification(User user, String message, LocalDateTime activationTime);

    void createScheduledDriveReminders(User user, LocalDateTime driveStartTime);

    @Transactional
    void createAdminNotification(Integer rideId, Integer userId, String reason);

    List<UserNotification> getUserNotifications(User user);
}
