package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;

import java.time.LocalDate;
import java.util.List;

public interface BusinessService {
    AccommodationEntity updateValues(AccommodationEntity accommodationEntity);
    void updateAccommodationValues();
    List<String> filterRoomAvailable(List<RoomEntity> roomEntities);
    List<RoomEntity> filterCheckAvailability(List<String> uuidList, List<RoomEntity> roomEntities, LocalDate checkIn, LocalDate checkOut);
    List<ResponseAccommodationDTO> filterAccommodation(List<RoomEntity> available);
    void saveSearchResult(List<RoomEntity> availableRooms);
    void updateRoomValues();
}
