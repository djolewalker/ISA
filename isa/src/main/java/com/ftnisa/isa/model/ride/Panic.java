package com.ftnisa.isa.model.ride;


import com.ftnisa.isa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isa_panic")
@Data
@AllArgsConstructor
public class Panic {

    @Id
    @SequenceGenerator(name = "panicSeqGen", sequenceName = "panicSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "panicSeqGen")
    private Integer id;

    @Column(name = "panic_time")
    private LocalDateTime panicTime;

    @Column(name = "resolves")
    private boolean isResolved;

    @ManyToOne
    @JoinColumn(name = "resolved_by")
    private User resolvedBy;

    @Column(name = "resolve_time")
    private LocalDateTime resolveTime;

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

    public Panic(LocalDateTime panicTime, boolean isResolved, User resolvedBy, LocalDateTime resolveTime, User user, String panicReason, Ride ride) {
        this.panicTime = panicTime;
        this.isResolved = isResolved;
        this.resolvedBy = resolvedBy;
        this.resolveTime = resolveTime;
        this.user = user;
        this.panicReason = panicReason;
        this.ride = ride;
    }

    public LocalDateTime getPanicTime() {
        return panicTime;
    }

    public void setPanicTime(LocalDateTime panicTime) {
        this.panicTime = panicTime;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public User getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(User resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDateTime getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(LocalDateTime resolveTime) {
        this.resolveTime = resolveTime;
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
