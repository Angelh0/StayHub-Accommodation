package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BusinessService {
    AccommodationEntity updateValues(AccommodationEntity accommodationEntity);
    void updateAccommodationValues(List<RoomEntity> availableRooms);
    List<String> filterRoomAvailable(List<RoomEntity> roomEntities);
    List<RoomEntity> filterCheckAvailability(List<String> uuidList, List<RoomEntity> roomEntities, LocalDate checkIn, LocalDate checkOut);
    List<ResponseAccommodationDTO> filterAccommodation(List<RoomEntity> available);
    List<ResponseRoomDTO> returnRooms(List<RoomEntity> available, UUID uuidAccommodation);
    void validateAccommodationStatus(UUID uuidRoom);
    void validateRoomStatus(UUID uuidRoom, UUID uuidOwner, String blockStartDate, String blockEndDate);
    void updateRoomValues(UUID uuidRoom);
}
