package com.ftnisa.isa.event.resetPasswordRequested;

import com.ftnisa.isa.email.EmailService;
import com.ftnisa.isa.service.TokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnResetPasswordRequestedEventHandler implements ApplicationListener<OnResetPasswordRequestedEvent> {
    private final EmailService emailService;
    private final TokenService tokenService;

    public OnResetPasswordRequestedEventHandler(EmailService emailService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnResetPasswordRequestedEvent event) {
        var user = event.getUser();
        var token = tokenService.createResetToken(user);

        var recipientAddress = user.getEmail();
        var subject = "Reset password";
        var message = "Token to reset password:";

        emailService.sendSimpleMessage(recipientAddress, "no-reply@isa-uber.com",
                subject, message + "\r\n" + token);
    }
}
