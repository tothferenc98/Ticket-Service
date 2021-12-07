package com.epam.training.ticketservice.core.room.persistence.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoomTest {

    private static final Room room1Entity = new Room("Pedersoli", 5, 6);
    private static final Room room2Entity = new Room("Room2", 9, 5);

    @Test
    public void testEqualsRoomEntityShouldCallRoomEntityEqualsMethod() {
        // Given - When - Then
        boolean a = room1Entity.equals(room1Entity);
        Assertions.assertTrue(a);
    }

    @Test
    public void testNotEqualsRoomEntityShouldCallRoomEntityEqualsMethod() {
        // Given - When - Then
        boolean a = room1Entity.equals(room2Entity);
        Assertions.assertFalse(a);
    }
}
