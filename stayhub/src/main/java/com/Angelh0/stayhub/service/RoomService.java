package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.RoomAdminDTO;
import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO, UUID uuid);
    List<RoomAdminDTO> getAllRooms();
    RoomDTO modifiedRooms(RoomDTO roomDTO, UUID uuid);
    RoomDTO getRooms(UUID uuid);
    void deleteByUuid(UUID uuid);

}
