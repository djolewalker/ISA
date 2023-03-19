package com.ftnisa.isa.event.resetPasswordRequested;

import com.ftnisa.isa.model.user.User;
import org.springframework.context.ApplicationEvent;

public class OnResetPasswordRequestedEvent extends ApplicationEvent {
    private final User user;

    public OnResetPasswordRequestedEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
