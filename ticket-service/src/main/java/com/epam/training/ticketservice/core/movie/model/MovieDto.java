package com.epam.training.ticketservice.core.movie.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class MovieDto {

    private final String title;
    private final String genre;
    private final int length;

    public MovieDto(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }


    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }


    @Override
    public String toString() {
        return String.format("%s (%s, %s minutes)", title, genre, length);
    }

    public static class Builder {
        private String title;
        private String genre;
        private int length;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public MovieDto build() {
            return new MovieDto(title, genre, length);
        }
    }
}