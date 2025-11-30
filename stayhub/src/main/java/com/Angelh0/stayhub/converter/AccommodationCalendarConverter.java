package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.DraftAccommodation.AccommodationCalendarDTO;
import com.Angelh0.stayhub.entity.AccommodationCalendarEntity;
import org.springframework.stereotype.Component;

@Component
public class AccommodationCalendarConverter {

    public AccommodationCalendarDTO entityToDTO (AccommodationCalendarEntity calendar) {
        AccommodationCalendarDTO calendarDTO = new AccommodationCalendarDTO();

        calendarDTO.setUuidCalendar(calendar.getUuidCalendar());
        calendarDTO.setCalendarMonth(calendar.getCalendarMonth());

        return calendarDTO;
    }

    public AccommodationCalendarEntity dtoToEntity (AccommodationCalendarDTO calendarDTO) {
        AccommodationCalendarEntity calendar = new AccommodationCalendarEntity();

        calendar.setUuidCalendar(calendarDTO.getUuidCalendar());
        calendar.setCalendarMonth(calendarDTO.getCalendarMonth());

        return calendar;
    }
}