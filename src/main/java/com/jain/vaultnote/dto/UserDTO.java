package com.jain.vaultnote.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "User name must not be empty")
    private String userName;
    private String email;
    @NotEmpty(message = "Password must not be empty")
    private String password;
}
