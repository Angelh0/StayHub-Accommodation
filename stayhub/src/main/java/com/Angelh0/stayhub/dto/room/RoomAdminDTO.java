package com.Angelh0.stayhub.dto.room;

import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.Angelh0.stayhub.enums.RoomEnums.RoomType;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RoomAdminDTO {

    private UUID uuidAcc;
    private UUID uuid;
    private String city;
    private int room;
    private int capacity;
    private int beds;
    private RoomType type;
    private double price;
    private double areaInSquareMeters;
    private RoomStatus roomStatus;
    private StatusType status;
}
