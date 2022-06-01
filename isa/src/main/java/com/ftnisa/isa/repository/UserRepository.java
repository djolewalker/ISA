package com.ftnisa.isa.repository;

import com.ftnisa.isa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
