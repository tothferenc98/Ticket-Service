package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.impl.MovieServiceImpl;
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
public class MovieCommands {

    private final MovieServiceImpl movieServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public MovieCommands(MovieServiceImpl movieServiceImpl, UserServiceImpl userServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @ShellMethod(value = "List movies.", key = "list movies")
    public List<String> listMovies() {
        List<MovieDto> movies = movieServiceImpl.listAll();

        if (movies.size() == 0) {
            return List.of("There are no movies at the moment");
        } else {
            return movies.stream().map(MovieDto::toString).collect(Collectors.toList());
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create movie")
    public void createMovie(String title, String genre, int length) {
        MovieDto movie = MovieDto.builder()
                .withTitle(title)
                .withGenre(genre)
                .withLength(length)
                .build();
        movieServiceImpl.create(movie);

    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update movie")
    public void updateMovie(String title, String genre, int length) {
        MovieDto movie = MovieDto.builder()
                .withTitle(title)
                .withGenre(genre)
                .withLength(length)
                .build();
        movieServiceImpl.updateMovie(movie);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Delete movie")
    public void deleteMovie(String title) {
        movieServiceImpl.deleteMovie(title);
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userServiceImpl.getLoggedInUser();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

}
