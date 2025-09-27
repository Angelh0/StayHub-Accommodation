package com.Angelh0.stayhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class SearchRoomDTO {


    private UUID uuidRoom;
    private UUID searchUuid;
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int room;
    private int capacity;
    private boolean available;
}