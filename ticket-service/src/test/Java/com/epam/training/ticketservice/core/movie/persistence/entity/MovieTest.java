package com.epam.training.ticketservice.core.movie.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MovieTest {

    private static final Movie movie1Entity = new Movie("Dune", "Sci-fi", 155);
    private static final Movie movie2Entity = new Movie("Venom", "Action", 97);

    @Test
    public void testEqualsMovieEntityShouldCallMovieEntityEqualsMethod() {
        // Given - When - Then
        boolean a = movie1Entity.equals(movie1Entity);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsMovieEntityShouldCallMovieEntityEqualsMethod() {
        // Given - When - Then
        boolean a = movie1Entity.equals(movie2Entity);
        Assertions.assertFalse(a);
    }
}
