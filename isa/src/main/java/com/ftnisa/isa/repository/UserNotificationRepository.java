package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    @Query("select n from UserNotification n where n.user = :user")
    List<UserNotification> findByUser(@Param("user") User user);
}
