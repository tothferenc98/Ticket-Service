package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.room.impl.RoomServiceImpl;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.constraints.AssertTrue;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ScreeningServiceImplTest {

    private static final String DUMMY_MOVIE_TITLE = "Title";
    private static final String DUMMY_MOVIE_GENRE = "Genre";
    private static final int DUMMY_MOVIE_LENGTH = 30;
    private static final String DUMMY_ROOM_NAME = "Name";
    private static final int DUMMY_ROOM_ROWS = 5;
    private static final int DUMMY_ROOM_COLUMNS = 5;
    private static final String DUMMY_START_DATE = "2021-01-01 12:00";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

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
                .withTitle("Other")
                .withGenre(DUMMY_MOVIE_GENRE)
                .withLength(DUMMY_MOVIE_LENGTH)
                .build();
        Room room = Room.builder()
                .name("DUMMY_ROOM_NAME")
                .rows(DUMMY_ROOM_ROWS)
                .columns(DUMMY_ROOM_COLUMNS)
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 1, 1, 11, 45))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle(DUMMY_MOVIE_TITLE))
                .thenReturn(Optional.of(new Movie(DUMMY_MOVIE_TITLE, DUMMY_MOVIE_GENRE, DUMMY_MOVIE_LENGTH)));
        Mockito.when(roomRepository.findRoomByName(DUMMY_ROOM_NAME))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "There is an overlapping screening";

        // When
        String actual = underTest.createScreening(DUMMY_MOVIE_TITLE, DUMMY_ROOM_NAME, DUMMY_START_DATE);

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(DUMMY_MOVIE_TITLE);
        Mockito.verify(roomRepository).findRoomByName(DUMMY_ROOM_NAME);
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenPassedScreeningsBeginningOverlapsWithOtherScreeningsEnding2() {

        Movie otherMovie = Movie.builder()
                .withTitle("Other")
                .withGenre(DUMMY_MOVIE_GENRE)
                .withLength(DUMMY_MOVIE_LENGTH)
                .build();
        Room room = Room.builder()
                .name("DUMMY_ROOM_NAME")
                .rows(DUMMY_ROOM_ROWS)
                .columns(DUMMY_ROOM_COLUMNS)
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 1, 1, 12, 00))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle(DUMMY_MOVIE_TITLE))
                .thenReturn(Optional.of(new Movie(DUMMY_MOVIE_TITLE, DUMMY_MOVIE_GENRE, DUMMY_MOVIE_LENGTH)));
        Mockito.when(roomRepository.findRoomByName(DUMMY_ROOM_NAME))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "There is an overlapping screening";

        // When
        String actual = underTest.createScreening(DUMMY_MOVIE_TITLE, DUMMY_ROOM_NAME, DUMMY_START_DATE);

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(DUMMY_MOVIE_TITLE);
        Mockito.verify(roomRepository).findRoomByName(DUMMY_ROOM_NAME);
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }


    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenPassedScreeningOverlapsWithOtherScreeningsBreakPeriod() {

        Movie otherMovie = Movie.builder()
                .withTitle("Other")
                .withGenre(DUMMY_MOVIE_GENRE)
                .withLength(DUMMY_MOVIE_LENGTH)
                .build();
        Room room = Room.builder()
                .name("DUMMY_ROOM_NAME")
                .rows(DUMMY_ROOM_ROWS)
                .columns(DUMMY_ROOM_COLUMNS)
                .build();
        Screening screeningToOverlapWith =
                Screening.builder()
                        .id(null)
                        .movie(otherMovie)
                        .room(room)
                        .startDateTime(LocalDateTime.of(2021, 1, 1, 11, 25))
                        .build();
        Mockito.when(movieRepository.findMovieByTitle(DUMMY_MOVIE_TITLE))
                .thenReturn(Optional.of(new Movie(DUMMY_MOVIE_TITLE, DUMMY_MOVIE_GENRE, DUMMY_MOVIE_LENGTH)));
        Mockito.when(roomRepository.findRoomByName(DUMMY_ROOM_NAME))
                .thenReturn(Optional.of(room));
        Mockito.when(screeningRepository.findScreeningsByRoom(room)).thenReturn(List.of(screeningToOverlapWith));
        String expected = "This would start in the break period after another screening in this room";

        // When
        String actual = underTest.createScreening(DUMMY_MOVIE_TITLE, DUMMY_ROOM_NAME, DUMMY_START_DATE);

        // Then
        Mockito.verify(movieRepository).findMovieByTitle(DUMMY_MOVIE_TITLE);
        Mockito.verify(roomRepository).findRoomByName(DUMMY_ROOM_NAME);
        Mockito.verify(screeningRepository).findScreeningsByRoom(room);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void tesfdgd() {
        boolean a= screeningDto.equals(screeningDto);
        Assertions.assertTrue(a);
    }

    @Test
    public void tesfdgdFalse() {
        boolean a= screeningDto.equals(screeningDto2);
        Assertions.assertFalse(a);
    }

/*
    @Test
    public void testDeleteScreeningShouldThrowUnknownScreeningExceptionWhenScreeningIsNotExists() {
        // Given
        Mockito.when(screeningRepository
            .findByMovieEntity_TitleAndAndRoomEntity_NameAndStartTime(CREATE_SCREENING_DTO_1.getMovieName(),
                CREATE_SCREENING_DTO_1.getRoomName(), CREATE_SCREENING_DTO_1.getTime()))
            .thenReturn(Optional.empty());
        // When
        Assertions
            .assertThrows(UnknownScreeningException.class, () -> underTest.deleteScreening(CREATE_SCREENING_DTO_1));
        // Then
        Mockito.verify(screeningRepository)
            .findByMovieEntity_TitleAndAndRoomEntity_NameAndStartTime(CREATE_SCREENING_DTO_1.getMovieName(),
                CREATE_SCREENING_DTO_1.getRoomName(), CREATE_SCREENING_DTO_1.getTime());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testCreateScreeningShouldCallScreeningRepositoryWhenScreeningInputIsValid()
    {
        // Given
        Mockito.when(movieRepository.findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName()))
            .thenReturn(Optional.of(MOVIE_ENTITY));
        Mockito.when(roomRepository.findByName(CREATE_SCREENING_DTO_1.getRoomName()))
            .thenReturn(Optional.of(ROOM_ENTITY));
        Mockito.when(screeningRepository.save(SCREENING_ENTITY_1)).thenReturn(SCREENING_ENTITY_1);
        // When
        underTest.createScreening(CREATE_SCREENING_DTO_1);
        // Then
        Mockito.verify(movieRepository).findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName());
        Mockito.verify(roomRepository).findByName(CREATE_SCREENING_DTO_1.getRoomName());
        Mockito.verify(screeningRepository).save(SCREENING_ENTITY_1);
        Mockito.verifyNoMoreInteractions(movieRepository);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningCreationExceptionWhenScreeningMovieTitleIsNotFound() {
        // Given
        Mockito.when(movieRepository.findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName()))
            .thenReturn(Optional.empty());
        Mockito.when(roomRepository.findByName(CREATE_SCREENING_DTO_1.getRoomName()))
            .thenReturn(Optional.of(ROOM_ENTITY));
        // When
        Assertions
            .assertThrows(ScreeningCreationException.class, () -> underTest.createScreening(CREATE_SCREENING_DTO_1));
        // Then
        Mockito.verify(movieRepository).findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName());
        Mockito.verify(roomRepository).findByName(CREATE_SCREENING_DTO_1.getRoomName());
        Mockito.verifyNoMoreInteractions(movieRepository);
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningCreationExceptionWhenScreeningRoomNameIsNotFound() {
       // Given
        Mockito.when(movieRepository.findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName()))
            .thenReturn(Optional.of(MOVIE_ENTITY));
        Mockito.when(roomRepository.findByName(CREATE_SCREENING_DTO_1.getRoomName())).thenReturn(Optional.empty());
        // When
        Assertions
            .assertThrows(ScreeningCreationException.class, () -> underTest.createScreening(CREATE_SCREENING_DTO_1));
        // Then
        Mockito.verify(movieRepository).findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName());
        Mockito.verify(roomRepository).findByName(CREATE_SCREENING_DTO_1.getRoomName());
        Mockito.verifyNoMoreInteractions(movieRepository);
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningCreationExceptionWhenScreeningRoomAndMovieAreNotFound() {
       // Given
        Mockito.when(movieRepository.findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName()))
            .thenReturn(Optional.empty());
        Mockito.when(roomRepository.findByName(CREATE_SCREENING_DTO_1.getRoomName())).thenReturn(Optional.empty());
        // When
        Assertions
            .assertThrows(ScreeningCreationException.class, () -> underTest.createScreening(CREATE_SCREENING_DTO_1));
        // Then
        Mockito.verify(movieRepository).findMovieEntityByTitle(CREATE_SCREENING_DTO_1.getMovieName());
        Mockito.verify(roomRepository).findByName(CREATE_SCREENING_DTO_1.getRoomName());
        Mockito.verifyNoMoreInteractions(movieRepository);
        Mockito.verifyNoMoreInteractions(screeningRepository);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }
*/
}
