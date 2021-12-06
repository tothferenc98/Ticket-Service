package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.screening.impl.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.impl.UserServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class ScreeningCommands {

    private final ScreeningServiceImpl screeningService;
    private final UserServiceImpl userService;

    @Autowired
    public ScreeningCommands(ScreeningServiceImpl screeningService, UserServiceImpl userService) {
        this.screeningService = screeningService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create a screening")
    public String createScreening(String movieTitle, String roomName, String startDateTime) {
        String result = screeningService.createScreening(movieTitle, roomName, startDateTime);
        return result;
    }

    @ShellMethod(key = "list screenings", value = "List screenings.")
    public List<String> list() {
        List<ScreeningDto> screenings = screeningService.listAll();

        if (screenings.size() == 0) {
            return List.of("There are no screenings");
        } else {
            return screenings.stream().map(ScreeningDto::toString).collect(Collectors.toList());
        }
    }


    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete screening.")
    public void delete(String movieTitle, String roomName, String start) {
        screeningService.deleteScreening(movieTitle, roomName, start);
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.getLoggedInUser();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }


}

