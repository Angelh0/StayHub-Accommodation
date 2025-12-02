package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateRoomDTO {
    private int capacity;
    private int beds;
    private double price;
    private RoomStatus roomStatus;

    @JsonFormat(pattern="d/M/yyyy")
    private LocalDate blockStartDate;

    @JsonFormat(pattern="d/M/yyyy")
    private LocalDate blockEndDate;

    private List<String> photos;
}
