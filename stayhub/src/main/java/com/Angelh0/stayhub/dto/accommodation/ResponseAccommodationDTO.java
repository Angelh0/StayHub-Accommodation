package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.dto.DraftAccommodation.AccommodationCalendarDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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

    @JsonFormat(pattern = "dd-MM-yy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yy HH:mm:ss")
    private LocalDateTime updatedAt;

    @Min(value = 1, message = "La estancia minima no puede ser inferior a 1")
    private Integer minStay;

    private Integer maxStay;
    private AccommodationCalendarDTO availabilityCalendar;
    private List<String> photos;

    private List<RoomDTO> rooms;
}