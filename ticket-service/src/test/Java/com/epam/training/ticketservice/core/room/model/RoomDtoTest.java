package com.epam.training.ticketservice.core.room.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoomDtoTest {

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

    @Test
    public void testEqualsRoomDtoShouldCallRoomDtoEqualsMethod() {
        // Given - When - Then
        boolean a = room1Dto.equals(room1Dto);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsRoomDtoShouldCallRoomDtoEqualsMethod() {
        // Given - When - Then
        boolean a = room1Dto.equals(room2Dto);
        Assertions.assertFalse(a);
    }


    @Test
    public void testToStringRoomDtoShouldCallRoomDtoToStringMethod() {
        // Given - When - Then
        Assertions.assertEquals("Room Pedersoli with 30 seats, 5 rows and 6 columns", room1Dto.toString());
    }
}
