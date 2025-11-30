package com.Angelh0.stayhub.dto.room;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoomAvailabilityDTO {

    private UUID uuidRoom;
    private boolean available;
    private String message;
}
