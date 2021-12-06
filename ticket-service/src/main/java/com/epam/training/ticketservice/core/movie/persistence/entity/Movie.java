package com.epam.training.ticketservice.core.movie.persistence.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    String title;
    String genre;
    int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public Movie() {
    }

    public static Movie.Builder builder() {
        return new Movie.Builder();
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

    public static class Builder {
        private String title;
        private String genre;
        private int length;

        public Movie.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Movie.Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Movie.Builder withLength(int length) {
            this.length = length;
            return this;
        }

        public Movie build() {
            return new Movie(title, genre, length);
        }
    }

    public String toString() {
        return String.format("%s (%s, %d minutes)", this.title, this.genre, this.length);
    }

}
