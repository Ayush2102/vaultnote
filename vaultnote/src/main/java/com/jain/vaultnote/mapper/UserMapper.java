package com.jain.vaultnote.mapper;

import com.jain.vaultnote.dto.UserDTO;
import com.jain.vaultnote.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRoles(List.of("ROLE_USER"));
        return user;
    }
}
