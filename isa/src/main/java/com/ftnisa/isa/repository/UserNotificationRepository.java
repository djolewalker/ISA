package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {

}
