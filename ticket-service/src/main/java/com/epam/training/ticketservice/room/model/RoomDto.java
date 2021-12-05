package com.epam.training.ticketservice.room.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Builder
@EqualsAndHashCode
@Getter
public class RoomDto {
    private final String name;
    private final int rows;
    private final int columns;

    public RoomDto(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }


    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomDto)) {
            return false;
        }
        RoomDto roomDto = (RoomDto) o;
        return getRows() == roomDto.getRows()
                && getColumns() == roomDto.getColumns()
                && Objects.equals(getName(), roomDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRows(), getColumns());
    }

    @Override
    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns",
                this.name, rows * columns, this.rows, this.columns);
    }


    public static class Builder {
        private String name;
        private int rows;
        private int columns;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder withColumns(int columns) {
            this.columns = columns;
            return this;
        }


        public RoomDto build() {
            return new RoomDto(name, rows, columns);
        }
    }
}