package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.room.model.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> listAll();

    void create(RoomDto room);

    void updateRoom(RoomDto room);

    void deleteRoom(String title);
}
