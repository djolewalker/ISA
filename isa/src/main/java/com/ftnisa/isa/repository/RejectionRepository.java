package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.ride.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectionRepository extends JpaRepository<Rejection, Integer> {

}
