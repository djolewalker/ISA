package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.user.UpdateUserRequest;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToString")
    UserResponse toUserResponse(User user);

    @Named("rolesToString")
    default List<String> rolesToString(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }

    User toUser (UpdateUserRequest updateUserRequest);
}
