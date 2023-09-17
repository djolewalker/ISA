package com.ftnisa.isa.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
public class UserNotificationDto {
    private int id;
    private String description;
    private String activationTime;
}
