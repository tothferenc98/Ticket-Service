package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository,
                                RoomRepository roomRepository,
                                MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public List<ScreeningDto> listAll() {

        return screeningRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public String createScreening(String movieTitle, String roomName, String dateTime) {
        Objects.requireNonNull(movieTitle, "movieTitle cannot be null");
        Objects.requireNonNull(roomName, "roomName cannot be null");
        Objects.requireNonNull(dateTime, "dateTime cannot be null");
        Movie movie = movieRepository.findMovieByTitle(movieTitle).orElseThrow();
        Room room = roomRepository.findRoomByName(roomName).orElseThrow();
        LocalDateTime startDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<ScreeningDto> screeningsInRoom = screeningRepository.findScreeningsByRoom(room).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        for (ScreeningDto screening : screeningsInRoom) {
            if (startDateTime.isEqual(screening.getStartDateTime())
                    || startDateTime.isAfter(screening.getStartDateTime())
                    && startDateTime.plusMinutes(movie.getLength()).isBefore(screening.getStartDateTime()
                    .plusMinutes(screening.getMovie().getLength()))
                    || startDateTime.isBefore(screening.getStartDateTime()
                    .plusMinutes(screening.getMovie().getLength()))
                    && startDateTime.plusMinutes(movie.getLength()).isAfter(screening.getStartDateTime()
                    .plusMinutes(screening.getMovie().getLength()))) {
                return "There is an overlapping screening";
            }
            if (startDateTime.isAfter(screening.getStartDateTime()
                    .plusMinutes(screening.getMovie().getLength()))
                    && startDateTime.isBefore((screening.getStartDateTime()
                    .plusMinutes(screening.getMovie().getLength()).plusMinutes(10)))) {
                return "This would start in the break period after another screening in this room";
            }
        }

        screeningRepository.save(Screening.builder()
                .movie(movie)
                .room(room)
                .startDateTime(startDateTime)
                .build());
        return "Screening created";
    }

    @Override
    @Transactional
    public void deleteScreening(String movieTitle, String roomName, String dateTime) {
        Objects.requireNonNull(movieTitle, "movieTitle cannot be null");
        Objects.requireNonNull(roomName, "roomName cannot be null");
        Objects.requireNonNull(dateTime, "dateTime cannot be null");
        Room room = roomRepository.findRoomByName(roomName).orElseThrow();
        LocalDateTime startDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        screeningRepository.deleteByRoomAndStartDateTime(room, startDateTime);
    }

    private ScreeningDto convertEntityToDto(Screening screeningEntity) {
        return ScreeningDto.builder()
                .movie(MovieDto.builder()
                        .withLength(screeningEntity.getMovie().getLength())
                        .withGenre(screeningEntity.getMovie().getGenre())
                        .withTitle(screeningEntity.getMovie().getTitle())
                        .build())
                .room(RoomDto.builder()
                        .withName(screeningEntity.getRoom().getName())
                        .withRows(screeningEntity.getRoom().getRows())
                        .withColumns(screeningEntity.getRoom().getColumns())
                        .build())
                .startDateTime(screeningEntity.getStartDateTime())
                .build();
    }
}

