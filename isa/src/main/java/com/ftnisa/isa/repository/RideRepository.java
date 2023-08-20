package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    public List<Ride> findByRideStatus(RideStatus rideStatus);

    public List<Ride> findByDriver(Driver driver);

    public List<Ride> findByDriverAndStartTimeBetween(Driver driver, LocalDateTime beginning, LocalDateTime end);

    public Ride findOneByDriverAndRideStatus(Driver driver, RideStatus rideStatus);

    public List<Ride> findByDriverAndRideStatus(Driver driver, RideStatus rideStatus);

    public Ride findOneById(Integer id);

    public List<Ride> findByPassengerAndRideStatus(User passenger, RideStatus rideStatus);

    public List<Ride> findByPassenger(User passenger);

    public List<Ride> findByPassengerAndStartTimeBetween(User passenger, Date date1, Date date2);




}
