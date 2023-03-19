package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByName(String name);
}
