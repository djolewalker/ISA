package com.ftnisa.isa.model.ride;



import com.ftnisa.isa.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "isa_rejection")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rejection {
    @Id
    @SequenceGenerator(name = "rejectionSeqGen", sequenceName = "rejectionSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejectionSeqGen")
    private Integer id;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rejection_time")
    private LocalDateTime rejectionTime;

    public Rejection(String rejectionReason, User user, LocalDateTime rejectionTime) {
        this.rejectionReason = rejectionReason;
        this.user = user;
        this.rejectionTime = rejectionTime;
    }

    public Rejection(String rejectionReason, LocalDateTime rejectionTime) {
        this.rejectionReason = rejectionReason;
        this.rejectionTime = rejectionTime;
    }
}
