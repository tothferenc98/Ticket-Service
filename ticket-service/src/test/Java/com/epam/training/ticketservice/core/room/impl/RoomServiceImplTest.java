package com.epam.training.ticketservice.core.room.impl;


import com.epam.training.ticketservice.core.room.impl.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.entity.Room;
import com.epam.training.ticketservice.core.room.persistence.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomServiceImplTest {

    private static final Room room1Entity = new Room("Pedersoli", 5 , 6);
    private static final Room room2Entity = new Room("Room2", 9, 5);

    private static final RoomDto room1Dto = new RoomDto.Builder()
            .withName("Pedersoli")
            .withRows(5)
            .withColumns(6)
            .build();

    private static final RoomDto room2Dto = new RoomDto.Builder()
            .withName("Room2")
            .withRows(9)
            .withColumns(5)
            .build();

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);


    @Test
    public void testGetRoomListShouldCallRoomRepositoryAndReturnADtoList() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of(room1Entity, room2Entity));
        List<RoomDto> expected = List.of(room1Dto, room2Dto);

        // When
        List<RoomDto> actual = underTest.listAll();

        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findAll();
    }

    @Test
    public void testCreateRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(room1Entity)).thenReturn(room1Entity);

        // When
        underTest.create(room1Dto);

        // Then
        verify(roomRepository).save(room1Entity);
    }

    /*@Test
    public void testUpdateRoomShouldCallRoomRepositoryWhenTheInputRoomIsExistsInDatabase()
    {
        // Given
        Mockito.when(roomRepository.findRoomByName(room1Dto.getName()))
                .thenReturn(Optional.of(room1Entity));
        Mockito.when(roomRepository.save(room1Entity)).thenReturn(room1Entity);

        // When
        underTest.updateRoom(room1Dto);

        // Then
        Mockito.verify(roomRepository).findRoomByName(room1Dto.getName());
        Mockito.verify(roomRepository).save(room1Entity);
        Mockito.verifyNoMoreInteractions(roomRepository);
    }*/

    @Test
    public void testDeleteRoomShouldCallRoomRepositoryWhenRoomNameIsExistsInDatabase()
    {
        // Given
        Mockito.when(roomRepository.findRoomByName("room title")).thenReturn(Optional.of(room1Entity));
        // When
        underTest.deleteRoom("room title");
        // Then
        Mockito.verify(roomRepository).findRoomByName(("room title"));
        Mockito.verify(roomRepository).delete((room1Entity));
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

}
