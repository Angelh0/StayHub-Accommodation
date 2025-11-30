package com.Angelh0.stayhub.dto.accommodation;

import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccommodationDTO {
    private String name;
    private String description;
}
