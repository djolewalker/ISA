package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.user.UserRegister;
import com.ftnisa.isa.model.user.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll ();
    User register(UserRegister userRegister);
}
