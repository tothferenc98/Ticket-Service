package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@EqualsAndHashCode
public final class ScreeningDto {

    private final MovieDto movie;
    private final RoomDto room;
    private final LocalDateTime startDateTime;

    public ScreeningDto(MovieDto movie, RoomDto room, LocalDateTime startDateTime) {
        this.movie = movie;
        this.room = room;
        this.startDateTime = startDateTime;
    }


    public MovieDto getMovie() {
        return movie;
    }

    public RoomDto getRoom() {
        return room;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie.getGenre() + ", " + movie.getLength()
                + " minutes), screened in room " + room.getName() + ", at "
                + startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
