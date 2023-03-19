package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.auth.RegisterRequest;
import com.ftnisa.isa.event.resetPasswordRequested.OnResetPasswordRequestedEvent;
import com.ftnisa.isa.event.verificationRequested.OnVerificationRequestedEvent;
import com.ftnisa.isa.exception.HandledException;
import com.ftnisa.isa.exception.ResourceConflictException;
import com.ftnisa.isa.model.token.TokenType;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.TokenRepository;
import com.ftnisa.isa.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleService roleService, TokenService tokenService,
                           ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegisterRequest registerRequest, String role) {
        var userCheck = userRepository.findByUsername(registerRequest.getUsername());
        if (userCheck != null) {
            throw new ResourceConflictException(userCheck.getId(), "Username already exists!");
        }

        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        List<Role> roles = roleService.findByName(role);
        if (roles.isEmpty()) {
            throw new NotFoundException("Role not found");
        }

        user.setRoles(roles);
        user.setEnabled(false);

        var createdUser = this.userRepository.save(user);

        eventPublisher.publishEvent(new OnVerificationRequestedEvent(createdUser));

        return createdUser;
    }

    @Override
    public void verify(String verificationToken) {
        var token = tokenService.findToken(verificationToken, TokenType.VERIFICATION);
        var user = token.getUser();

        user.setEnabled(true);
        userRepository.save(user);

        tokenService.removeToken(token);
    }

    @Override
    public void forgotPassword(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email '" + email + "' not found!");
        }

        eventPublisher.publishEvent(new OnResetPasswordRequestedEvent(user));
    }

    @Override
    public void resetPassword(String password, String resetPasswordToken) {
        var token = tokenService.findToken(resetPasswordToken, TokenType.RESET_PASSWORD);
        var user = token.getUser();

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        tokenService.removeToken(token);
    }
}
