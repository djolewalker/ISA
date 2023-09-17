package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.notification.AdminNotificationDto;
import com.ftnisa.isa.dto.notification.UserNotificationDto;
import com.ftnisa.isa.model.notification.AdminNotification;
import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    UserNotificationDto userNotificationToUserNotificationDto(UserNotification userNotification);

    List<UserNotificationDto> userNotificationsToUserNotificationsDto(List<UserNotification> userNotification);

    AdminNotificationDto adminNotificationToAdminNotificationDto(AdminNotification adminNotification);

    List<AdminNotificationDto> adminNotificationsToAdminNotificationsDto(List<AdminNotification> adminNotification);

}
