package com.ftnisa.isa.event.verificationRequested;

import com.ftnisa.isa.model.user.User;
import org.springframework.context.ApplicationEvent;

public class OnVerificationRequestedEvent extends ApplicationEvent {
    private final User user;

    public OnVerificationRequestedEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
