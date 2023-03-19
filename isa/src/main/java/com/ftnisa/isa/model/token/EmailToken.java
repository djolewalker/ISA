package com.ftnisa.isa.model.token;

import com.ftnisa.isa.model.user.User;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "isa_email_token")
public class EmailToken {
    public static final int VERIFICATION_EXPIRATION = 60 * 24;
    public static final int RESET_EXPIRATION = 60 * 2;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryAt;

    private TokenType type;

    public EmailToken() {
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public EmailToken(String token, User user, TokenType type, int expirationInMinutes) {
        this.token = token;
        this.user = user;
        this.type = type;
        this.expiryAt = calculateExpiryDate(expirationInMinutes);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Date getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryAt = expiryDate;
    }
}
