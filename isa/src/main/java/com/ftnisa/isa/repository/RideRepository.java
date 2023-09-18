package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    public List<Ride> findByRideStatus(RideStatus rideStatus);

    public List<Ride> findByDriver(Driver driver);

    public List<Ride> findByDriverAndStartTimeBetween(Driver driver, LocalDateTime beginning, LocalDateTime end);

    public List<Ride> findByDriverAndStartTimeBetweenAndRideStatus(Driver driver, LocalDateTime beginning, LocalDateTime end, RideStatus rideStatus);

    public Ride findOneByDriverAndRideStatus(Driver driver, RideStatus rideStatus);

    public List<Ride> findAllByDriverAndRideStatus(Driver driver, RideStatus rideStatus);

    public List<Ride> findAllByDriverAndRideStatusIsNotOrderByIdDesc(Driver driver, RideStatus rideStatus);

    public Ride findOneById(Integer id);

    public List<Ride> findAllByPassengerAndRideStatus(User passenger, RideStatus rideStatus);

    public List<Ride> findByPassenger(User passenger);

    public List<Ride> findByPassengerAndStartTimeBetween(User passenger, LocalDateTime date1, LocalDateTime date2);

    public List<Ride> findByPassengerAndStartTimeBetweenAndRideStatus(User passenger, LocalDateTime date1, LocalDateTime date2, RideStatus rideStatus);

    public List<Ride> findByStartTimeBetweenAndRideStatus(LocalDateTime date1, LocalDateTime date2, RideStatus rideStatus);

    public Ride findByDriverAndRideStatusIn(Driver driver, List<RideStatus> statuses);

    public Ride findByPassengerAndRideStatusIn(User user, List<RideStatus> statuses);

    public List<Ride> findByPassengerAndRideStatusIsNotOrderByIdDesc(User user, RideStatus rideStatus);
}
