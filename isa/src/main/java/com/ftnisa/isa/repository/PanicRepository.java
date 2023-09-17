package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanicRepository extends JpaRepository<Panic, Integer> {
    Panic findByRide(Ride ride);
}
