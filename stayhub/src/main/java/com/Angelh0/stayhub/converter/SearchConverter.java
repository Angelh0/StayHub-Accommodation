package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchConverter {

    public SearchRoomEntity convertToEntity(SearchRoomDTO searchRoomDTO) {
        SearchRoomEntity searchRoomEntity = new SearchRoomEntity();
        searchRoomEntity.setSearchUuid(searchRoomDTO.getSearchUuid());
        searchRoomEntity.setSearchUuid(searchRoomDTO.getSearchUuid());
        searchRoomEntity.setCity(searchRoomDTO.getCity());
        searchRoomEntity.setRoom(searchRoomDTO.getRoom());
        searchRoomEntity.setCapacity(searchRoomDTO.getCapacity());
        searchRoomEntity.setCheckIn(searchRoomDTO.getCheckIn());
        searchRoomEntity.setCheckOut(searchRoomDTO.getCheckOut());

        return searchRoomEntity;
    }

    public SearchRoomDTO convertToDTO(SearchRoomEntity searchRoomEntity) {
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setSearchUuid(searchRoomEntity.getSearchUuid());
        searchRoomDTO.setCity(searchRoomEntity.getCity());
        searchRoomDTO.setRoom(searchRoomEntity.getRoom());
        searchRoomDTO.setCapacity(searchRoomEntity.getCapacity());
        searchRoomDTO.setCheckIn(searchRoomEntity.getCheckIn());
        searchRoomDTO.setCheckOut(searchRoomEntity.getCheckOut());

        return searchRoomDTO;
    }
}
