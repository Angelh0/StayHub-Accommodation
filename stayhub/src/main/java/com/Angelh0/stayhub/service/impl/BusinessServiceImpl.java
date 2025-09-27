package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.service.BusinessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Override
    public AccommodationEntity updateValues(AccommodationEntity accommodationEntity) {

        Double priceMax = null;
        Double priceMin = null;
        int contador = 0;

        List<RoomEntity> roomEntity = accommodationEntity.getRooms();

        for (RoomEntity room : roomEntity) {

            if (room.getStatus() == StatusType.Available) {
                contador ++;

                if (priceMax == null || room.getPrice() > priceMax) {
                    priceMax = room.getPrice();
                }

                if (priceMin == null || room.getPrice() < priceMin) {
                    priceMin = room.getPrice();
                }
            }
        }

        accommodationEntity.setPriceMin(priceMin);
        accommodationEntity.setPriceMax(priceMax);
        accommodationEntity.setAvailability(contador);

        return accommodationEntity;
    }
}
