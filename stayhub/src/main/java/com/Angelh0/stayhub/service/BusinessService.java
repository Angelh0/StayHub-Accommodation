package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BusinessService {
    AccommodationEntity updateValues(AccommodationEntity accommodationEntity);
    void updateAccommodationValues();
    List<String> filterRoomAvailable(List<RoomEntity> roomEntities);
    List<RoomEntity> filterCheckAvailability(List<String> uuidList, List<RoomEntity> roomEntities, LocalDate checkIn, LocalDate checkOut);
    List<ResponseAccommodationDTO> filterAccommodation(List<RoomEntity> available);
    void validateAccommodationStatus(UUID uuidRoom);
    void validateRoomStatus(UUID uuidRoom, LocalDate blockStartDate, LocalDate blockEndDate);
    void saveSearchResult(List<RoomEntity> availableRooms);
    void updateRoomValues();
}
