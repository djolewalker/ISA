package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.notification.AdminNotification;
import com.ftnisa.isa.model.notification.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminNotificationRepository extends JpaRepository<AdminNotification, Integer> {

}
