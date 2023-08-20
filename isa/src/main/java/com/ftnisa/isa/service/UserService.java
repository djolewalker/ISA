package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.auth.RegisterRequest;
import com.ftnisa.isa.dto.user.CreateDriverRequest;
import com.ftnisa.isa.dto.user.UserRequest;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);

    List<User> findAll();

    User registerUser(RegisterRequest registerRequest, String role);

    void verify(String verificationToken);

    void forgotPassword(String email);

    void resetPassword(String password, String resetPasswordToken);

    User updateUserProfile(String username, UserRequest userRequest);

    User updateUser(int id, UserRequest userRequest);

    Driver registerDriver(CreateDriverRequest driverRequest);

    Driver findDriverById(int id);

}
