package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;

public interface MovieService {

    List<MovieDto> listAll();

    void create(MovieDto movie);

    void updateMovie(MovieDto movie);

    void deleteMovie(String title);
}
