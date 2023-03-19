package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.token.EmailToken;
import com.ftnisa.isa.model.token.TokenType;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<EmailToken, Integer> {
    EmailToken findByTokenAndType(String token, TokenType type);

    EmailToken findByUserAndType(User user, TokenType type);
}
