package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    String name;
    int rows;
    int columns;

    public String getName() {
        return name;
    }


    public int getRows() {
        return rows;
    }


    public int getColumns() {
        return columns;
    }

    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns",
                this.name, rows * columns, this.rows, this.columns);
    }
}
