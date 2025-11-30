package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateRoomDTO {
    private int capacity;
    private int beds;
    private double price;
    private RoomStatus roomStatus;
    private LocalDate blockStartDate;
    private LocalDate blockEndDate;
}
