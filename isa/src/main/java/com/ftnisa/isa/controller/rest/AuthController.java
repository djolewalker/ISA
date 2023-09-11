package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.auth.*;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.mapper.UserMapper;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.service.UserService;
import com.ftnisa.isa.util.TokenUtils;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {
    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper mapper;
    private final DriverService driverService;
    private SimpMessagingTemplate template;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {
        var credentials = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        var authentication = authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = (User) authentication.getPrincipal();
        var roles = user.getRoles().stream().map(Role::getName).toList();
        var jwt = tokenUtils.generateAccessToken(user.getUsername(), roles);
        var expTime = tokenUtils.getExpiredIn();

        if (user.hasRole(Role.DRIVER)) {
            driverService.activateDriver(user.getUsername());
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, tokenUtils.createAccessTokenCookie(jwt, expTime).toString());

        return ResponseEntity.ok().headers(responseHeaders).body(new JwtResponse(jwt, expTime));
    }

    @GetMapping("/signout")
    public ResponseEntity signOut(Principal principal) {
        var user = this.userService.findByUsername(principal.getName());

        if (user.hasRole(Role.DRIVER)) {
            driverService.deactivateDriver(user.getUsername());
            template.convertAndSend("/topic/driver/deactivated", user.getId());
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, tokenUtils.deleteAccessTokenCookie().toString());

        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/signup/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequest registerRequest) {
        var user = userService.registerUser(registerRequest, Role.USER);
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
