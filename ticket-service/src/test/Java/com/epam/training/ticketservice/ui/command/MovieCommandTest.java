package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.impl.MovieServiceImpl;
import com.epam.training.ticketservice.core.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieCommandTest {
    MovieServiceImpl movieService = Mockito.mock(MovieServiceImpl.class);
    UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
    MovieCommands movieCommand = new MovieCommands(movieService,userService);

    @Test
    public void test(){
        //given
        String result;
        //when
        movieCommand.createMovie("film","drama",154);
        //then
        assertEquals(Collections.emptyList(),movieService.listAll());
    }
}
