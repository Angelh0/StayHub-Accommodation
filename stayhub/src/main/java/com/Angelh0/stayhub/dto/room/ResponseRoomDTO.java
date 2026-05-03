package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseRoomDTO {

    private UUID uuid;
    private UUID uuidAccommodation;
    private String nameAccommodation;
    private UUID uuidOwner;
    private String city;
    private int room;
    private int capacity;
    private int beds;
    private RoomType type;
    private double totalPrice;
    private double areaInSquareMeters;
    private List<String> photos;
}
