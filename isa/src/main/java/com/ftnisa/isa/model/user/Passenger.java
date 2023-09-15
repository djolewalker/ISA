package com.ftnisa.isa.model.user;

import com.ftnisa.isa.model.ride.Ride;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "isa_passenger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {

    @OneToMany
    private Set<Ride> ridesList;
}
