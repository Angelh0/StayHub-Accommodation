package com.Angelh0.stayhub.dto;

import com.Angelh0.stayhub.enums.AccommodationType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResponseAccommodationDTO {

    private UUID uuid;
    private String name;
    private AccommodationType type;
    private String description;
    private String city;
    private String country;
    private int availability;
    private Double priceMax;
    private Double priceMin;
}