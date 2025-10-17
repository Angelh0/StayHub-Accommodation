package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchConverter {

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

    public SearchRoomDTO convertToDTO(RoomEntity roomEntity) {
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setCity(roomEntity.getAccommodation().getCity());
        searchRoomDTO.setCapacity(roomEntity.getCapacity());
        searchRoomDTO.setCheckOut(searchRoomDTO.getCheckOut());
        searchRoomDTO.setCheckIn(searchRoomDTO.getCheckIn());

        return searchRoomDTO;
    }
}
