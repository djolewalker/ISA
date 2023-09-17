package com.ftnisa.isa.dto.notification;

import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminNotificationDto {
    private Integer id;
    private RideDto ride;
    private String description;
    private String creationTime;
}
