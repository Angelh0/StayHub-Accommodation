package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.dto.DraftAccommodation.AccommodationCalendarDTO;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateAccommodationDTO {
    private String name;
    private String description;
    private Integer minStay;
    private Integer maxStay;
    private AccommodationCalendarDTO calendar;
    private List<String> photos;
}
