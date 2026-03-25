package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomAdminDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.dto.room.UpdateRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomDTO createRoom(RoomDTO roomDTO, UUID uuid, UUID userUUID);
    List<RoomAdminDTO> getAllRooms();
    RoomDTO modifiedRooms(UpdateRoomDTO updateRoomDTO, UUID uuid, UUID userUUID);
    ResponseRoomDTO getRooms(UUID uuid);
    void deleteByUuid(UUID uuid);
    List<RoomDTO> getMyRooms(UUID uuid);
}
