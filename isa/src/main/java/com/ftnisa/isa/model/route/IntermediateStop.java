package com.ftnisa.isa.model.route;

import com.ftnisa.isa.model.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "isa_intermediate_stop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntermediateStop {
    @Id
    @SequenceGenerator(name = "intermediateStopSeqGen", sequenceName = "intermediateStopSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "intermediateStopSeqGen")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location")
    @NotNull
    private Location location;

    @Column(name = "isa_order")
    private int order;

    @ManyToOne
    @JoinColumn(name = "route")
    @NotNull
    private Route route;
}
