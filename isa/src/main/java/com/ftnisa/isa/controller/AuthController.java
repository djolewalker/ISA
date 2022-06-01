package com.ftnisa.isa.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ftnisa.isa.dto.user.UserJwt;
import com.ftnisa.isa.dto.user.UserLogin;
import com.ftnisa.isa.dto.user.UserRegister;
import com.ftnisa.isa.exception.ResourceConflictException;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.service.UserService;
import com.ftnisa.isa.util.TokenUtils;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserJwt> createAuthenticationToken(@RequestBody UserLogin authenticationRequest)
    {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expTime = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserJwt(jwt, expTime));
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody UserRegister userRegister) {
        User userCheck = this.userService.findByUsername(userRegister.getUsername());
        if ( userCheck != null) {
            throw new ResourceConflictException(userCheck.getId(), "Username already exists!");
        }
        User user = userService.register(userRegister);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
