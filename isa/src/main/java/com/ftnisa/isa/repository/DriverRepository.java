package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
    public List<Driver> findByActiveTrue();
    Driver findByUsername(String username);
}
