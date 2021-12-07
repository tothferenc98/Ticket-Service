package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.junit.jupiter.api.Assertions;
import java.time.LocalDateTime;

public class ScreeningDtoTest {

    public static final MovieDto movieDto = MovieDto.builder()
            .withTitle("Dune")
            .withGenre("Sci-fi")
            .withLength(155)
            .build();
    public static final RoomDto roomDto = RoomDto.builder()
            .withName("roomName")
            .withRows(5)
            .withColumns(6)
            .build();


    public static final ScreeningDto screeningDto = ScreeningDto.builder()
        .movie(movieDto)
        .room(roomDto)
        .startDateTime(LocalDateTime.of(2021, 12, 6, 10, 30))
        .build();
    public static final ScreeningDto screeningDto2 = ScreeningDto.builder()
        .movie(movieDto)
        .room(roomDto)
        .startDateTime(LocalDateTime.of(2021, 12, 6, 14, 30))
        .build();


    @Test
    public void testEqualsScreeningDtoShouldCallScreeningDtoEqualsMethod() {
        // Given - When - Then
        boolean a= screeningDto.equals(screeningDto);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsScreeningDtoShouldCallScreeningDtoEqualsMethod() {
        // Given - When - Then
        boolean a = screeningDto.equals(screeningDto2);
        Assertions.assertFalse(a);
    }


    @Test
    public void testToStringScreeningDtoShouldCallScreeningDtoToStringMethod() {
        // Given - When - Then
        Assertions.assertEquals("Dune (Sci-fi, 155 minutes), screened in room roomName, at 2021-12-06 10:30", screeningDto.toString());
    }
}
