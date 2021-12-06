package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.impl.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
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
public class RoomCommands {

    private final RoomServiceImpl roomService;
    private final UserServiceImpl userService;

    @Autowired
    public RoomCommands(RoomServiceImpl roomService, UserServiceImpl userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethod(key = "list rooms", value = "Lists rooms")
    public List<String> list() {
        List<RoomDto> rooms = roomService.listAll();

        if (rooms.size() == 0) {
            return List.of("There are no rooms at the moment");
        } else {
            return rooms.stream().map(RoomDto::toString).collect(Collectors.toList());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create a room")
    public void createRoom(String name, int rows, int columns) {
        RoomDto room = RoomDto.builder()
                .withName(name)
                .withRows(rows)
                .withColumns(columns)
                .build();
        roomService.create(room);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Update existing room")
    public void updateRoom(String name, int rows, int columns) {
        RoomDto room = RoomDto.builder()
                .withName(name)
                .withRows(rows)
                .withColumns(columns)
                .build();
        roomService.updateRoom(room);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Delete room")
    public void delete(String name) {
        roomService.deleteRoom(name);
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.getLoggedInUser();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

}

