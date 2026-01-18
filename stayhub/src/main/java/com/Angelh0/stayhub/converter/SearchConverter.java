package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.LastSearchEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchConverter {

    public SearchRoomDTO convertToDTO(LastSearchEntity lastSearchEntity) {
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setSearchUuid(lastSearchEntity.getSearchUuid());
        searchRoomDTO.setCity(lastSearchEntity.getCity());
        searchRoomDTO.setRoom(lastSearchEntity.getRoom());
        searchRoomDTO.setCapacity(lastSearchEntity.getCapacity());
        searchRoomDTO.setCheckIn(lastSearchEntity.getCheckIn());
        searchRoomDTO.setCheckOut(lastSearchEntity.getCheckOut());

        return searchRoomDTO;
    }

    public SearchRoomDTO convertToDTO(RoomEntity roomEntity) {
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setCity(roomEntity.getAccommodation().getCity());
        searchRoomDTO.setCapacity(roomEntity.getCapacity());
        searchRoomDTO.setCheckOut(searchRoomDTO.getCheckOut());
        searchRoomDTO.setCheckIn(searchRoomDTO.getCheckIn());

        return searchRoomDTO;
    }
}
