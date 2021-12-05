package com.epam.training.ticketservice.room.persistence.repository;

import com.epam.training.ticketservice.room.persistence.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findAll();

    Optional<Room> findRoomByName(String name);
}
