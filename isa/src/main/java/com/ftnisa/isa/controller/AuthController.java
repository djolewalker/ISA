package com.ftnisa.isa.controller;

import com.ftnisa.isa.dto.auth.*;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.mapper.UserMapper;
import com.ftnisa.isa.model.user.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.service.UserService;
import com.ftnisa.isa.util.TokenUtils;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper mapper;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, UserService userService,
            UserMapper mapper) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {
        var credentials = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        var authentication = authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = (User) authentication.getPrincipal();
        var roles = user.getRoles().stream().map(role -> role.getName()).toList();
        var jwt = tokenUtils.generateAccessToken(user.getUsername(), roles);
        var expTime = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new JwtResponse(jwt, expTime));
    }

    @PostMapping("/signup/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequest registerRequest) {
        var user = userService.registerUser(registerRequest, Role.USER);
        return new ResponseEntity<>(mapper.toUserResponse(user), HttpStatus.CREATED);
    }

    @PostMapping("/signup/driver")
    public ResponseEntity<UserResponse> createDriver(@RequestBody RegisterRequest registerRequest) {
        var user = userService.registerUser(registerRequest, Role.DRIVER);
        return new ResponseEntity<>(mapper.toUserResponse(user), HttpStatus.CREATED);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyUser(@RequestParam String verificationToken) {
        userService.verify(verificationToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        userService.forgotPassword(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest,
            @RequestParam String resetPasswordToken) {
        userService.resetPassword(resetPasswordRequest.getPassword(), resetPasswordToken);
        return ResponseEntity.ok().build();
    }
}
