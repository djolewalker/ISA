package com.ftnisa.isa.service;

import com.ftnisa.isa.model.notification.AdminNotification;
import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.AdminNotificationRepository;
import com.ftnisa.isa.repository.RideRepository;
import com.ftnisa.isa.repository.UserNotificationRepository;
import com.ftnisa.isa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class NotificationServiceImpl implements NotificationService {


    private final UserNotificationRepository userNotificationRepository;

    private final AdminNotificationRepository adminNotificationRepository;

    private final RideRepository rideRepository;

    private final UserRepository userRepository;

    public NotificationServiceImpl(UserNotificationRepository userNotificationRepository, RideRepository rideRepository, UserRepository userRepository, AdminNotificationRepository adminNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.adminNotificationRepository = adminNotificationRepository;
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



    @Override
    public void createAdminNotification(Integer rideId, Integer userId, String reason){

        Ride ride = rideRepository.findById(rideId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        String message = String.format(
                "HITNO: korisnik %s (broj %d) je pritisnuo PANIC dugme na vožni br %d. Naveden razlog: %s",
                user.getUsername(),
                userId,
                rideId,
                reason
                );

        AdminNotification adminNotification = new AdminNotification(
                ride,
                user,
                message,
                LocalDateTime.now()
        );

        adminNotificationRepository.save(adminNotification);
    }



}
