package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.notification.UserNotificationDto;
import com.ftnisa.isa.model.notification.UserNotification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    UserNotificationDto userNotificationToUserNotificationDto(UserNotification userNotification);

    List<UserNotificationDto> userNotificationsToUserNotificationsDto(List<UserNotification> userNotification);
}
