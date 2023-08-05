package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
}
