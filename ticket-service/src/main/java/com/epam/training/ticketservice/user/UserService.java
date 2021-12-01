package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.user.model.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> login(String username, String password);

    Optional<UserDto> logout();

    Optional<UserDto> getLoggedInUser();

    void registerUser(String username, String password);
}
