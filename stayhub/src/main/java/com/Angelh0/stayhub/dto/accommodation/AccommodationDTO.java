package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.dto.DraftAccommodation.AccommodationCalendarDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.AccommodationCalendarEntity;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private AccommodationStatus status;
    private int availability;
    private Double priceMax;
    private Double priceMin;

    @Min(value = 1, message = "La estancia minima no puede ser inferior a 1")
    private int minStay;

    private int maxStay;
    private AccommodationCalendarDTO availabilityCalendar;
    private List<String> photos;
    private List<RoomDTO> rooms;
}