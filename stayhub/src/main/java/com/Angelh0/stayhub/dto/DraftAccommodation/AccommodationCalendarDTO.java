package com.Angelh0.stayhub.dto.DraftAccommodation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccommodationCalendarDTO {

    private UUID uuidCalendar;
    private List<Integer> calendarMonth;
}
