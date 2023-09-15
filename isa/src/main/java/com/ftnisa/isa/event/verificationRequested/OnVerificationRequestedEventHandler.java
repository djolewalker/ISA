package com.ftnisa.isa.event.verificationRequested;

import com.ftnisa.isa.configuration.AppConfiguration;
import com.ftnisa.isa.email.EmailService;
import com.ftnisa.isa.service.TokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnVerificationRequestedEventHandler implements ApplicationListener<OnVerificationRequestedEvent> {
    private final AppConfiguration appConfiguration;
    private final EmailService emailService;
    private final TokenService tokenService;

    public OnVerificationRequestedEventHandler(AppConfiguration appConfiguration, EmailService emailService,
            TokenService tokenService) {
        this.appConfiguration = appConfiguration;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    @Override
    public void onApplicationEvent(OnVerificationRequestedEvent event) {
        var user = event.getUser();
        var token = tokenService.createVerificationToken(user);

        var recipientAddress = user.getEmail();
        var subject = "Registration Confirmation";
        var confirmationUrl = appConfiguration.getAppUrl()  + "api/auth/verify-email/verify-email?token=" + token;
        var message = "Confirm your email address by clicking following url:";

        emailService.sendSimpleMessage(recipientAddress, "no-reply@isa-uber.com",
                subject, message + "\r\n" + confirmationUrl);
    }
}
