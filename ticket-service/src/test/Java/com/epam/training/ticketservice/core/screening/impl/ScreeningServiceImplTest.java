package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
public class ScreeningServiceImplTest {


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

    private ScreeningServiceImpl underTest;
    private RoomRepository roomRepository;
    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        roomRepository = Mockito.mock(RoomRepository.class);
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, roomRepository, movieRepository);
    }

    @Test
    public void testGetScreeningShouldCallScreeningRepositoryAndReturnADtoList() {
       // Given
        Mockito.when(screeningRepository.findAll()).thenReturn(List.of(screeningEntity1, screeningEntity2));
        List<ScreeningDto> expected = List.of(screeningDto, screeningDto2);

        // When
        List<ScreeningDto> actual = underTest.listAll();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(screeningRepository).findAll();
        Mockito.verifyNoMoreInteractions(screeningRepository);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenPassedScreeningsBeginningOverlapsWithOtherScreeningsEnding() {

        Movie otherMovie = Movie.builder()
                .withTitle("Film2")
                .withGenre(movieEntity.getGenre())
                .withLength(movieEntity.getLength())
                .build();
        Room room = Room.builder()
                .name("Room2")
                .rows(roomEntity.getRows())
                .columns(roomEntity.getColumns())
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 12, 6, 10, 15))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle( movieEntity.getTitle()))
                .thenReturn(Optional.of(Movie.builder()
                        .withTitle(movieEntity.getTitle())
                        .withGenre(movieEntity.getGenre())
                        .withLength(movieEntity.getLength())
                        .build()));
        Mockito.when(roomRepository.findRoomByName(roomEntity.getName()))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "There is an overlapping screening";

        // When
        String actual = underTest.createScreening(movieEntity.getTitle(), roomEntity.getName(), "2021-12-06 10:30");

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(movieEntity.getTitle());
        Mockito.verify(roomRepository).findRoomByName(roomEntity.getName());
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenPassedScreeningsBeginningOverlapsWithOtherScreeningsEnding2() {

        Movie otherMovie = Movie.builder()
                .withTitle("Film2")
                .withGenre(movieEntity.getGenre())
                .withLength(movieEntity.getLength())
                .build();
        Room room = Room.builder()
                .name("Room2")
                .rows(roomEntity.getRows())
                .columns(roomEntity.getColumns())
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 12, 6, 10, 30))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle( movieEntity.getTitle()))
                .thenReturn(Optional.of(Movie.builder()
                        .withTitle(movieEntity.getTitle())
                        .withGenre(movieEntity.getGenre())
                        .withLength(movieEntity.getLength())
                        .build()));
        Mockito.when(roomRepository.findRoomByName(roomEntity.getName()))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "There is an overlapping screening";

        // When
        String actual = underTest.createScreening(movieEntity.getTitle(), roomEntity.getName(), "2021-12-06 10:30");

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(movieEntity.getTitle());
        Mockito.verify(roomRepository).findRoomByName(roomEntity.getName());
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }


    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenPassedScreeningOverlapsWithOtherScreeningsBreakPeriod() {

        Movie otherMovie = Movie.builder()
                .withTitle("Film2")
                .withGenre(movieEntity.getGenre())
                .withLength(movieEntity.getLength())
                .build();
        Room room = Room.builder()
                .name("Room2")
                .rows(roomEntity.getRows())
                .columns(roomEntity.getColumns())
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 12, 6, 7, 50))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle( movieEntity.getTitle()))
                .thenReturn(Optional.of(Movie.builder()
                        .withTitle(movieEntity.getTitle())
                        .withGenre(movieEntity.getGenre())
                        .withLength(movieEntity.getLength())
                        .build()));
        Mockito.when(roomRepository.findRoomByName(roomEntity.getName()))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "This would start in the break period after another screening in this room";

        // When
        String actual = underTest.createScreening(movieEntity.getTitle(), roomEntity.getName(), "2021-12-06 10:30");

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(movieEntity.getTitle());
        Mockito.verify(roomRepository).findRoomByName(roomEntity.getName());
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }



}
