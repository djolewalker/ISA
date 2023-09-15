package com.ftnisa.isa.service;

import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Integer id) {
        Optional<Role> optionalRole = this.roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public List<Role> findByName(String name) {
        return this.roleRepository.findByName(name);
    }
}