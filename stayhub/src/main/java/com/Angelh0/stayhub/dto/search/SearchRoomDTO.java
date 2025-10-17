package com.Angelh0.stayhub.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class SearchRoomDTO {

    private UUID searchUuid;

    private String city;

    @JsonFormat(pattern="d/M/yyyy")
    private LocalDate checkIn;

    @JsonFormat(pattern="d/M/yyyy")
    private LocalDate checkOut;

    private int room;

    private int capacity;

    private boolean available;
}