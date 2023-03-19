package com.ftnisa.isa.service;

import com.ftnisa.isa.model.token.EmailToken;
import com.ftnisa.isa.model.token.TokenType;
import com.ftnisa.isa.model.user.User;

public interface TokenService {
    String createVerificationToken(User user);
    String createResetToken(User user);
    EmailToken findToken(String token, TokenType type);
    void removeToken(EmailToken token);
}
