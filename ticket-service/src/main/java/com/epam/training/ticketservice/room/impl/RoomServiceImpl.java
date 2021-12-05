package com.epam.training.ticketservice.room.impl;

import com.epam.training.ticketservice.room.model.RoomDto;
import com.epam.training.ticketservice.room.persistence.entity.Room;
import com.epam.training.ticketservice.room.persistence.repository.RoomRepository;
import com.epam.training.ticketservice.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> listAll() {
        return roomRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());

    }

    @Override
    public void create(RoomDto roomDto) {
        Objects.requireNonNull(roomDto, "room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "room title cannot be null");
        Objects.requireNonNull(roomDto.getRows(), "room row cannot be null");
        Objects.requireNonNull(roomDto.getColumns(), "room column cannot be null");
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(RoomDto room) {
        Objects.requireNonNull(room, "room cannot be null");
        Objects.requireNonNull(room.getName(), "room title cannot be null");
        Objects.requireNonNull(room.getRows(), "room row cannot be null");
        Objects.requireNonNull(room.getColumns(), "room column cannot be null");
        Room updatedRoomEntity = Room.builder()
                .name(room.getName())
                .rows(room.getRows())
                .columns(room.getColumns())
                .build();
        roomRepository.save(updatedRoomEntity);
    }

    @Override
    public void deleteRoom(String name) {
        Optional<Room> room = roomRepository.findRoomByName(name);
        roomRepository.delete(room.get());
    }

    private RoomDto convertEntityToDto(Room roomEntity) {
        return RoomDto.builder()
                .withName(roomEntity.getName())
                .withRows(roomEntity.getRows())
                .withColumns(roomEntity.getColumns())
                .build();
    }

}
