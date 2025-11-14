package com.Angelh0.stayhub.exception.RoomException;

public class RoomContainsReservation extends RuntimeException {
    public RoomContainsReservation(String message) {
        super(message);
    }
}
