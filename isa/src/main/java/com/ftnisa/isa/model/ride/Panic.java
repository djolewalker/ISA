package com.ftnisa.isa.model.ride;


import com.ftnisa.isa.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isa_panic")
public class Panic {

    @Id
    @SequenceGenerator(name = "panicSeqGen", sequenceName = "panicSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "panicSeqGen")
    private Integer id;

    @Column(name = "panic_time")
    private LocalDateTime panicTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "panic_reason")
    private String panicReason;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public Panic() {
    }

    public Panic(LocalDateTime panicTime, User user, String panicReason, Ride ride) {
        this.panicTime = panicTime;
        this.user = user;
        this.panicReason = panicReason;
        this.ride = ride;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getPanicTime() {
        return panicTime;
    }

    public void setPanicTime(LocalDateTime panicTime) {
        this.panicTime = panicTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPanicReason() {
        return panicReason;
    }

    public void setPanicReason(String panicReason) {
        this.panicReason = panicReason;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
