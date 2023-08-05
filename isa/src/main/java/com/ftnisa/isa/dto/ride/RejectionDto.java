package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.User;

import java.time.LocalDateTime;

public class RejectionDto {


    private String rejectionReason;


    private LocalDateTime rejectionTime;

    public RejectionDto() {
    }

    public RejectionDto(String rejectionReason, LocalDateTime rejectionTime) {
        this.rejectionReason = rejectionReason;
        this.rejectionTime = rejectionTime;
    }



    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }


    public LocalDateTime getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(LocalDateTime rejectionTime) {
        this.rejectionTime = rejectionTime;
    }
}
