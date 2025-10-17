package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomAdminDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO, UUID uuid);
    List<RoomAdminDTO> getAllRooms();
    RoomDTO modifiedRooms(RoomDTO roomDTO, UUID uuid);
    ResponseRoomDTO getRooms(UUID uuid);
    void deleteByUuid(UUID uuid);

}
