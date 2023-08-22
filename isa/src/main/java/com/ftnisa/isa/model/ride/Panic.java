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
@NoArgsConstructor
@AllArgsConstructor
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

    public Panic(LocalDateTime panicTime, User user, String panicReason, Ride ride) {
        this.panicTime = panicTime;
        this.user = user;
        this.panicReason = panicReason;
        this.ride = ride;
    }
}
