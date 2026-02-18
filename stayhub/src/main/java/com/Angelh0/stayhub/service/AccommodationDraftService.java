package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;

import java.util.UUID;

public interface AccommodationDraftService {
    boolean checkBasicCreate (UUID uuidAccommodation);
    AccommodationDTO stayConfiguration(UUID uuidAccommodation, AccommodationDTO accommodationDTO, UUID uuidOwner);
    boolean checkAddRooms (UUID uuidAccommodation);
    AccommodationDTO availabilityCalendar (UUID uuidAccommodation, AccommodationDTO accommodationDTO, UUID uuidOwner);
    boolean checkAvailabilityCalendar (UUID uuidAccommodation);
    AccommodationDTO addPhotos (UUID uuidAccommodation, AccommodationDTO accommodationDTO, UUID uuidOwner);
    boolean checkAddPhotos (UUID uuidAccommodation);
    AccommodationDTO publishAccommodation(UUID uuid);
    boolean checkPublishAccommodation (UUID uuid);

    boolean checkStayAccommodation(UUID uuidAccommodation, UUID uuidUser);
    boolean checkMonthAvailability(UUID uuidAccommodation, UUID uuidUser);
}
