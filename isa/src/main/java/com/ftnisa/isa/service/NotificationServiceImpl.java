package com.ftnisa.isa.service;

import com.ftnisa.isa.mapper.NotificationMapper;
import com.ftnisa.isa.model.notification.AdminNotification;
import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.AdminNotificationRepository;
import com.ftnisa.isa.repository.RideRepository;
import com.ftnisa.isa.repository.UserNotificationRepository;
import com.ftnisa.isa.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final UserNotificationRepository userNotificationRepository;
    private final AdminNotificationRepository adminNotificationRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private SimpMessagingTemplate template;
    private NotificationMapper mapper;
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void createInstantNotification(User user, String message) {
        UserNotification userNotification = new UserNotification(message, LocalDateTime.now(), user);
        userNotificationRepository.save(userNotification);
        template.convertAndSendToUser(user.getUsername(), "/queue/notification",
                mapper.userNotificationToUserNotificationDto(userNotification));
    }

    @Override
    public void createScheduledNotification(User user, String message, LocalDateTime activationTime) {
        var userNotification = new UserNotification(message, activationTime, user);
        userNotificationRepository.save(userNotification);
        var timezoneActivationTime = activationTime.toInstant(OffsetDateTime.now().getOffset());
        taskScheduler.schedule(new ScheduleNotification(userNotification, template), timezoneActivationTime);
    }

    @Override
    public void createScheduledDriveReminders(User user, LocalDateTime driveStartTime) {
        var isScheduled = driveStartTime.isAfter(LocalDateTime.now());
        if (isScheduled) {
            long j = Math.min(15, ChronoUnit.MINUTES.between(LocalDateTime.now(), driveStartTime));
            j = j - j % 5;
            for (long i = 5; i <= j; i += 5) {
                String message = String.format("Vaše vozilo stiže za %d minuta", i);
                LocalDateTime time = driveStartTime.minusMinutes(i);
                createScheduledNotification(user, message, time);
            }
        }

        createScheduledNotification(user, "Vaše vozilo je na adresi.", driveStartTime);
    }

    @Override
    public void createAdminNotification(Integer rideId, Integer userId, String reason) {

        var ride = rideRepository.findById(rideId).orElseThrow();
        var user = userRepository.findById(userId).orElseThrow();
        var message = String.format(
                "HITNO: korisnik %s (broj %d) je pritisnuo PANIC dugme na vožni br %d. Naveden razlog: %s",
                user.getUsername(),
                userId,
                rideId,
                reason);

        var adminNotification = new AdminNotification(ride, user, message);

        adminNotificationRepository.save(adminNotification);
        template.convertAndSendToUser(user.getUsername(), "/topic/notification/admin",
                mapper.adminNotificationToAdminNotificationDto(adminNotification));
    }

    @Override
    public List<UserNotification> getUserNotifications(User user) {
        return userNotificationRepository.findByUserAndActivationTimeBeforeOrderByActivationTimeDesc(user, LocalDateTime.now());
    }

    @Override
    public List<AdminNotification> getAdminNotifications() {
        return adminNotificationRepository.findAll(Sort.by(Sort.Direction.DESC, "creationTime"));
    }


    @AllArgsConstructor
    @Getter
    private class ScheduleNotification implements Runnable {
        private UserNotification userNotification;
        private SimpMessagingTemplate template;

        @Override
        public void run() {
            template.convertAndSendToUser(userNotification.getUser().getUsername(),
                    "/queue/notification",
                    mapper.userNotificationToUserNotificationDto(userNotification));
        }
    }
}
