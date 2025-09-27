package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.entity.SearchRoomEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SearchService {

    SearchRoomDTO getChecks();
    List<RoomDTO> searchAdvanced(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut);
    SearchRoomDTO searchGetRooms(UUID uuid);
}
