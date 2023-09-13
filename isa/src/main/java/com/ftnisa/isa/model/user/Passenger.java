package com.ftnisa.isa.model.user;

import com.ftnisa.isa.model.ride.Ride;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "isa_passenger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {

    @OneToMany
    private Set<Ride> ridesList;
}
