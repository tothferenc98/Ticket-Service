package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.entity.User;
import lombok.EqualsAndHashCode;

import java.util.Objects;

public class UserDto {

    private final String username;
    private final User.Role role;

    public UserDto(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserDto{"
                + "username='"
                + username
                + '\''
                + ", role="
                + role
                + '}';
    }
}
