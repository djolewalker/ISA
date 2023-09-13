package com.ftnisa.isa.model.token;

import com.ftnisa.isa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "isa_email_token")
@Data
@NoArgsConstructor
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
}
