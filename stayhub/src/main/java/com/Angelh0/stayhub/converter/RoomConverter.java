package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.RoomAdminDTO;
import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
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
        roomDTO.setCity(roomEntity.getAccommodation().getCity());
        roomDTO.setRoom(roomEntity.getRoom());
        roomDTO.setCapacity(roomEntity.getCapacity());
        roomDTO.setBeds(roomEntity.getBeds());
        roomDTO.setType(roomEntity.getType());
        roomDTO.setPrice(roomEntity.getPrice());
        roomDTO.setAreaInSquareMeters(roomEntity.getAreaInSquareMeters());
        roomDTO.setStatus(roomEntity.getStatus());

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
        roomAdminDTO.setStatus(roomEntity.getStatus());

        return roomAdminDTO;
    }

    public SearchRoomDTO convertToDTO(RoomEntity roomEntity) {
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setUuidRoom(roomEntity.getUuid());
        searchRoomDTO.setCity(roomEntity.getAccommodation().getCity());
        searchRoomDTO.setCapacity(roomEntity.getCapacity());
        searchRoomDTO.setCheckOut(searchRoomDTO.getCheckOut());
        searchRoomDTO.setCheckIn(searchRoomDTO.getCheckIn());

        return searchRoomDTO;
    }
}
