package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanicResponse {

    private Integer id;
    private LocalDateTime panicTime;
    private boolean isResolved;
    private String panicReason;
}
