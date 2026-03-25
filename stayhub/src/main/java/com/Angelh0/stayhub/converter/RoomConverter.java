package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomAdminDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import org.springframework.stereotype.Component;


@Component
public class RoomConverter {

    public RoomEntity convertDTOtoEntity(RoomDTO roomDTO) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoom(roomDTO.getRoom());
        roomEntity.setCapacity(roomDTO.getCapacity());
        roomEntity.setBeds(roomDTO.getBeds());
        roomEntity.setType(roomDTO.getType());
        roomEntity.setPrice(roomDTO.getPrice());
        roomEntity.setAreaInSquareMeters(roomDTO.getAreaInSquareMeters());
        roomEntity.setStatus(roomDTO.getStatus());

        return roomEntity;
    }

    public RoomDTO convertEntityToDTO(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setUuid(roomEntity.getUuid());
        roomDTO.setUuidOwner(roomEntity.getUuidOwner());
        roomDTO.setCity(roomEntity.getAccommodation().getCity());
        roomDTO.setRoom(roomEntity.getRoom());
        roomDTO.setCapacity(roomEntity.getCapacity());
        roomDTO.setBeds(roomEntity.getBeds());
        roomDTO.setType(roomEntity.getType());
        roomDTO.setPrice(roomEntity.getPrice());
        roomDTO.setAreaInSquareMeters(roomEntity.getAreaInSquareMeters());
        roomDTO.setStatus(roomEntity.getStatus());
        roomDTO.setCreatedAt(roomEntity.getCreatedAt());
        roomDTO.setUpdatedAt(roomEntity.getUpdatedAt());

        return roomDTO;
    }

    public RoomAdminDTO adminConverterToDTO(RoomEntity roomEntity) {
        RoomAdminDTO roomAdminDTO = new RoomAdminDTO();
        roomAdminDTO.setUuidAcc(roomEntity.getAccommodation().getUuid());
        roomAdminDTO.setUuid(roomEntity.getUuid());
        roomAdminDTO.setCity(roomEntity.getAccommodation().getCity());
        roomAdminDTO.setRoom(roomEntity.getRoom());
        roomAdminDTO.setCapacity(roomEntity.getCapacity());
        roomAdminDTO.setBeds(roomEntity.getBeds());
        roomAdminDTO.setType(roomEntity.getType());
        roomAdminDTO.setPrice(roomEntity.getPrice());
        roomAdminDTO.setAreaInSquareMeters(roomEntity.getAreaInSquareMeters());
        roomAdminDTO.setRoomStatus(roomEntity.getRoomStatus());
        roomAdminDTO.setStatus(roomEntity.getStatus());

        return roomAdminDTO;
    }

    public ResponseRoomDTO responseRoomToDTO(RoomEntity roomEntity) {
        ResponseRoomDTO responseRoomDTO = new ResponseRoomDTO();
        responseRoomDTO.setUuidOwner(roomEntity.getUuidOwner());
        responseRoomDTO.setUuid(roomEntity.getUuid());
        responseRoomDTO.setCity(roomEntity.getAccommodation().getCity());
        responseRoomDTO.setRoom(roomEntity.getRoom());
        responseRoomDTO.setBeds(roomEntity.getBeds());
        responseRoomDTO.setCapacity(roomEntity.getCapacity());
        responseRoomDTO.setType(roomEntity.getType());
        responseRoomDTO.setTotalPrice(roomEntity.getTotalPrice());
        responseRoomDTO.setAreaInSquareMeters(roomEntity.getAreaInSquareMeters());
        return responseRoomDTO;
    }
}
