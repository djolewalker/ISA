package com.ftnisa.isa.model.user;

import com.ftnisa.isa.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "isa_driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends User {
    @Column(name = "driver_license", nullable = false)
    private String driverLicense;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
