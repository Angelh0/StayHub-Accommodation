package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SearchService {

    SearchRoomDTO getChecks();
    List<ResponseAccommodationDTO> searchAdvanced(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut);
    List<ResponseRoomDTO> searchAdvancedRoom(UUID uuid);
    SearchRoomDTO searchGetRooms(UUID uuid);
}
