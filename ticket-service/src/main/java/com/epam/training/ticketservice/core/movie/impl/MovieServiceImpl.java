package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> listAll() {
        return movieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());

    }

    @Override
    public void create(MovieDto movieDto) {
        Objects.requireNonNull(movieDto, "Product cannot be null");
        Objects.requireNonNull(movieDto.getTitle(), "Product title cannot be null");
        Objects.requireNonNull(movieDto.getGenre(), "Product genre cannot be null");
        Objects.requireNonNull(movieDto.getLength(), "Product length cannot be null");
        Movie movie = new Movie(movieDto.getTitle(), movieDto.getGenre(), movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(MovieDto movie) {
        Objects.requireNonNull(movie, "Movie cannot be null");
        Objects.requireNonNull(movie.getTitle(), "Movie title cannot be null");
        Objects.requireNonNull(movie.getLength(), "Movie length cannot be null");
        Optional<Movie> oldMovieEntity = movieRepository.findMovieByTitle(movie.getTitle());
        Movie updatedMovieEntity = Movie.builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withLength(movie.getLength())
                .build();
        movieRepository.save(updatedMovieEntity);
    }

    @Override
    public void deleteMovie(String title) {
        Optional<Movie> movie = movieRepository.findMovieByTitle(title);
        movieRepository.delete(movie.get());
    }

    private MovieDto convertEntityToDto(Movie movieEntity) {
        return MovieDto.builder()
                .withTitle(movieEntity.getTitle())
                .withGenre(movieEntity.getGenre())
                .withLength(movieEntity.getLength())
                .build();
    }


}
