package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    List<Route> findAllByCreatedAtBeforeAndRideIsNull(Instant createdAt);
}
