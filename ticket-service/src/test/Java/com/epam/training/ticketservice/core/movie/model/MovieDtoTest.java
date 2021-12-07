package com.epam.training.ticketservice.core.movie.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MovieDtoTest {
    private static final MovieDto movie1Dto = new MovieDto.Builder()
            .withTitle("Dune")
            .withGenre("Sci-fi")
            .withLength(155)
            .build();

    private static final MovieDto movie2Dto = new MovieDto.Builder()
            .withTitle("Venom")
            .withGenre("Action")
            .withLength(97)
            .build();

    @Test
    public void testEqualsMovieDtoShouldCallMovieDtoEqualsMethod() {
        // Given - When - Then
        boolean a = movie1Dto.equals(movie1Dto);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsMovieDtoShouldCallMovieDtoEqualsMethod() {
        // Given - When - Then
        boolean a = movie1Dto.equals(movie2Dto);
        Assertions.assertFalse(a);
    }

    @Test
    public void testToStringMovieDtoShouldCallMovieDtoToStringMethod() {
        // Given - When - Then
        Assertions.assertEquals("Dune (Sci-fi, 155 minutes)", movie1Dto.toString());
    }


}
