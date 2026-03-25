package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;

import java.util.List;
import java.util.UUID;

public interface AccommodationService {

    RequestAccommodationDTO createAccommodation(RequestAccommodationDTO requestAccommodationDTO, UUID userUUID);
    List<ResponseAccommodationDTO> getAllAccommodations();
    ResponseAccommodationDTO modifiedAccommodation(UpdateAccommodationDTO updateAccommodationDTO, UUID uuid, UUID uuidUser);
    AccommodationDTO getAccommodationById(UUID uuid);
    void deleteAccommodation (UUID uuid);
    List<ResponseAccommodationDTO> getAccommodationByCity(String city);
    List<RoomDTO> getRooms(List<RoomEntity> rooms);
    List<AccommodationDTO> getMyAccommodations(UUID uuid);
}
