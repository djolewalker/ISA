package com.ftnisa.isa.service;

import com.ftnisa.isa.model.user.Role;

import java.util.List;

public interface RoleService {
    Role findById(Integer id);
    List<Role> findByName(String name);
}