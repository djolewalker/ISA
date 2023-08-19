package com.ftnisa.isa.controller;

import java.security.Principal;
import java.util.List;

import com.ftnisa.isa.dto.user.UpdateUserRequest;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.mapper.UserMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.ftnisa.isa.service.UserService;


@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;


    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> all() {
        var users = this.userService.findAll();
        var usersResponse = users.stream()
                .map(mapper::toUserResponse)
                .toList();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> info(Principal user) {
        var foundUser = this.userService.findByUsername(user.getName());
        return ResponseEntity.ok(mapper.toUserResponse(foundUser));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getById(@PathVariable Integer id) {
        var foundUser = this.userService.findById(id);
        return ResponseEntity.ok(mapper.toUserResponse(foundUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest updateUserRequest) {
        var user = this.userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(mapper.toUserResponse(user));
    }
}
