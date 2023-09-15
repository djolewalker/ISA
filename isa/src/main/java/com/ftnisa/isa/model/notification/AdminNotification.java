package com.ftnisa.isa.model.notification;

import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isa_admin_notification")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminNotification {

    @Id
    @SequenceGenerator(name = "adminNotificationSeqGen", sequenceName = "adminNotificationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminNotificationSeqGen")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    public AdminNotification(Ride ride, User user, String description, LocalDateTime creationTime) {
        this.ride = ride;
        this.user = user;
        this.description = description;
        this.creationTime = creationTime;
    }
}
