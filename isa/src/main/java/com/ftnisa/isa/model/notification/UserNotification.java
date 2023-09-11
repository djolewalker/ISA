package com.ftnisa.isa.model.notification;


import com.ftnisa.isa.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isa_notification")
public class UserNotification {

    @Id
    @SequenceGenerator(name = "notificationSeqGen", sequenceName = "notificationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSeqGen")
    private Integer id;

    private String description;

    private LocalDateTime activationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserNotification() {
    }

    public UserNotification(String description, LocalDateTime activationTime, User user) {
        this.description = description;
        this.activationTime = activationTime;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(LocalDateTime activationTime) {
        this.activationTime = activationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
