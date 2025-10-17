package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.enums.AccommodationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class AccommodationDTO {

    private UUID uuid;
    private String name;
    private AccommodationType type;
    private String description;
    private String city;
    private String country;
    private int availability;
    private Double priceMax;
    private Double priceMin;
    private List<RoomDTO> rooms;
}