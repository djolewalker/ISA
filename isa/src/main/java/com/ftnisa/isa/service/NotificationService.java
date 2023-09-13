package com.ftnisa.isa.service;

import com.ftnisa.isa.model.user.User;

import java.time.LocalDateTime;

public interface NotificationService {
    void createInstantNotification(User user, String message);

    void createScheduledNotification(User user, String message, LocalDateTime activationTime);

    void createScheduledDriveReminders(User user, LocalDateTime driveStartTime);
}
