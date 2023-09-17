package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    List<UserNotification> findByUserAndActivationTimeBeforeOrderByActivationTimeDesc(User user, LocalDateTime activationTime);
}
