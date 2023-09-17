package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.notification.AdminNotification;
import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminNotificationRepository extends JpaRepository<AdminNotification, Integer> {
}
