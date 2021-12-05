package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.user.UserService;
import com.epam.training.ticketservice.user.model.UserDto;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
public class UserCommand {

    private final UserService userService;

    public UserCommand(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        Optional<UserDto> user = userService.login(username, password);
        if (user.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        return null;
    }

    @ShellMethod(key = "sign out", value = "User logout")
    public void logout() {
        Optional<UserDto> user = userService.logout();
        /*if (user.isEmpty()) {
            return "You need to login first!";
        }
        return user.get().getUsername() + " is logged out!";*/
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String printLoggedInUser() {
        Optional<UserDto> userDto = userService.getLoggedInUser();
        if (userDto.isEmpty()) {
            return "You are not signed in";
        }
        return "Signed in with privileged account '" + userDto.get().getUsername() + "'";
    }

    @ShellMethod(key = "user register", value = "User registration")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }
}
