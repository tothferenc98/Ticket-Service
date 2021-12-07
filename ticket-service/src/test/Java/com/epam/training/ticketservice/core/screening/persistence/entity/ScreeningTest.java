package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ScreeningTest {

   private static final Room roomEntity = Room.builder()
            .name("roomName")
            .rows(5)
            .columns(6)
            .build();
    private static final Movie movieEntity = Movie.builder()
            .withTitle("Dune")
            .withGenre("Sci-fi")
            .withLength(155)
            .build();

    private static final Screening screeningEntity1 = Screening.builder()
        .id(null)
        .movie(movieEntity)
        .room(roomEntity)
        .startDateTime(LocalDateTime.of(2021, 12, 6, 10, 30))
        .build();

    private static final Screening screeningEntity2 = Screening.builder()
        .id(null)
        .movie(movieEntity)
        .room(roomEntity)
        .startDateTime(LocalDateTime.of(2021, 12, 6, 14, 30))
        .build();



    @Test
    public void testEqualsScreeningEntityShouldCallScreeningEntityEqualsMethod() {
        // Given - When - Then
        boolean a= screeningEntity1.equals(screeningEntity1);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsScreeningEntityShouldCallScreeningEntityEqualsMethod() {
        // Given - When - Then
        boolean a = screeningEntity1.equals(screeningEntity2);
        Assertions.assertFalse(a);
    }

}
