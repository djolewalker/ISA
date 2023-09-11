package com.ftnisa.isa.service;

import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class NotificationServiceImpl implements NotificationService {


    private final UserNotificationRepository userNotificationRepository;

    public NotificationServiceImpl(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }


    @Override
    public void createInstantNotification(User user, String message){
        UserNotification userNotification = new UserNotification(message, LocalDateTime.now(), user);
        userNotificationRepository.save(userNotification);

    }

    @Override
    public void createScheduledNotification(User user, String message, LocalDateTime activationTime){
        UserNotification userNotification = new UserNotification(message, activationTime, user);
        userNotificationRepository.save(userNotification);
    }

    @Override
    public void createScheduledDriveReminders(User user, LocalDateTime driveStartTime){

        if (driveStartTime.isAfter(LocalDateTime.now())){
            long j = Math.min(15, ChronoUnit.MINUTES.between(LocalDateTime.now(), driveStartTime));
            j = j - j%5;
            for (long i=5; i<=j; i+=5){
                String message = String.format("Vaše vozilo stiže za %d minuta", i);
                LocalDateTime time = driveStartTime.minusMinutes(i);
                createScheduledNotification(user, message, time);
            }
        }

        createScheduledNotification(user, "Vaše vozilo je na adresi.", driveStartTime);

    }



}
