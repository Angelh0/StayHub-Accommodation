package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseRoomDTO {

    private UUID uuid;
    private String city;
    private int room;
    private int capacity;
    private int beds;
    private RoomType type;
    private double totalPrice;
    private double areaInSquareMeters;
}
