package com.Angelh0.stayhub.dto;

import com.Angelh0.stayhub.enums.RoomType;
import com.Angelh0.stayhub.enums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoomDTO {

    private UUID uuid;
    private String city;
    private int room;
    private int capacity;
    private int beds;
    private RoomType type;
    private double price;
    private double areaInSquareMeters;
    private StatusType status;
}