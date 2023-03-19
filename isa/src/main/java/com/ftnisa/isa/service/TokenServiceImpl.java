package com.ftnisa.isa.service;

import com.ftnisa.isa.exception.HandledException;
import com.ftnisa.isa.model.token.EmailToken;
import com.ftnisa.isa.model.token.TokenType;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String createVerificationToken(User user) {
        var existingToken = tokenRepository.findByUserAndType(user, TokenType.VERIFICATION);
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        var token = new EmailToken(UUID.randomUUID().toString(), user,
                TokenType.VERIFICATION, EmailToken.VERIFICATION_EXPIRATION);
        tokenRepository.save(token);

        return token.getToken();
    }

    @Override
    public String createResetToken(User user) {
        var existingToken = tokenRepository.findByUserAndType(user, TokenType.RESET_PASSWORD);
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        var token = new EmailToken(UUID.randomUUID().toString(), user,
                TokenType.RESET_PASSWORD, EmailToken.RESET_EXPIRATION);
        tokenRepository.save(token);

        return token.getToken();
    }

    @Override
    public EmailToken findToken(String token, TokenType type) {
        var foundToken = tokenRepository.findByTokenAndType(token, type);
        if (foundToken == null) {
            throw new HandledException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        Calendar cal = Calendar.getInstance();
        if ((foundToken.getExpiryAt().getTime() - cal.getTime().getTime()) <= 0) {
            throw new HandledException(HttpStatus.BAD_REQUEST, "Token expired");
        }

        return foundToken;
    }

    @Override
    public void removeToken(EmailToken token) {
        tokenRepository.delete(token);
    }
}
