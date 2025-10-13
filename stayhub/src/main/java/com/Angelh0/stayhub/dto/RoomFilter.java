package com.Angelh0.stayhub.dto;

import com.Angelh0.stayhub.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomFilter {

    private List<String> uuidList;
    private List<RoomEntity> roomEntities;

}
