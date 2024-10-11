package com.event.mgmt.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private Set<String> roles;

}
