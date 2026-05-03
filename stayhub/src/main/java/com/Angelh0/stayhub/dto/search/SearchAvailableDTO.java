package com.Angelh0.stayhub.dto.search;

import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchAvailableDTO {
    List<RoomAvailabilityDTO> rooms;
}
