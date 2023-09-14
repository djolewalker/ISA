package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.DriverChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverChangeRequestRepository extends JpaRepository<DriverChangeRequest, Integer> {
}
