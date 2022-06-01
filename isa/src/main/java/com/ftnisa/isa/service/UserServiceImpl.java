package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.user.UserRegister;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findById(Integer id) throws AccessDeniedException {
        return userRepository.findById(id).orElseThrow(null);
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    @Override
    public User register(UserRegister userRegister) {
        User user = new User();
        user.setUsername(userRegister.getUsername());
        user.setEmail(userRegister.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));

        List<Role> roles = roleService.findByName("ROLE_USER");
        user.setRoles(roles);

        return this.userRepository.save(user);
    }
}
