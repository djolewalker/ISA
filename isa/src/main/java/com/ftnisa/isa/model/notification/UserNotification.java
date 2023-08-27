package com.ftnisa.isa.model.notification;


import com.ftnisa.isa.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "isa_notification")
public class Notification {

    @Id
    @SequenceGenerator(name = "notificationSeqGen", sequenceName = "notificationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSeqGen")
    private Integer id;

    private String description;

    private LocalDateTime activationTime;

    @ManyToOne
    private User user;

    

}
