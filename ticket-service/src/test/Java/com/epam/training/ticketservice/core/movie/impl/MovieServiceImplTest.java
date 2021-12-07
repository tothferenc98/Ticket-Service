package com.epam.training.ticketservice.core.movie.impl;


import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    private static final Movie movie1Entity = new Movie("Dune", "Sci-fi", 155);
    private static final Movie movie2Entity = new Movie("Venom", "Action", 97);

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

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);


    @Test
    public void testGetMovieListShouldCallMovieRepositoryAndReturnADtoList() {
        // Given
        when(movieRepository.findAll()).thenReturn(List.of(movie1Entity, movie2Entity));
        List<MovieDto> expected = List.of(movie1Dto, movie2Dto);

        // When
        List<MovieDto> actual = underTest.listAll();

        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }

    @Test
    public void testCreateMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(movie1Entity)).thenReturn(movie1Entity);

        // When
        underTest.create(movie1Dto);

        // Then
        verify(movieRepository).save(movie1Entity);
    }


    @Test
    public void testUpdateMovieShouldCallMovieRepositoryWhenTheInputMovieIsExistsInDatabase() {
        // Given
        Mockito.when(movieRepository.findMovieByTitle(movie1Dto.getTitle()))
                .thenReturn(Optional.of(movie1Entity));
        Mockito.when(movieRepository.save(movie1Entity)).thenReturn(movie1Entity);

        // When
        underTest.updateMovie(movie1Dto);

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(movie1Dto.getTitle());
        Mockito.verify(movieRepository).save(movie1Entity);
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testDeleteMovieShouldCallMovieRepositoryWhenMovieTitleIsExistsInDatabase() {
        // Given
        Mockito.when(movieRepository.findMovieByTitle("movie title")).thenReturn(Optional.of(movie1Entity));
        // When
        underTest.deleteMovie("movie title");
        // Then
        Mockito.verify(movieRepository).findMovieByTitle(("movie title"));
        Mockito.verify(movieRepository).delete((movie1Entity));
        Mockito.verifyNoMoreInteractions(movieRepository);
    }


}
