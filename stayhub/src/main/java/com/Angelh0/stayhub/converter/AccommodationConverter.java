package com.Angelh0.stayhub.converter;

import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import org.springframework.stereotype.Component;

@Component
public class AccommodationConverter {

    private final RoomConverter roomConverter;

    public AccommodationConverter(RoomConverter roomConverter) {

        this.roomConverter = roomConverter;
    }

    // Converter sin listado de alojamientos

    public ResponseAccommodationDTO responseToDTO(AccommodationEntity accommodationEntity) {
        ResponseAccommodationDTO responseAccommodationDTO = new ResponseAccommodationDTO();
        responseAccommodationDTO.setUuid(accommodationEntity.getUuid());
        responseAccommodationDTO.setName(accommodationEntity.getName());
        responseAccommodationDTO.setType(accommodationEntity.getType());
        responseAccommodationDTO.setDescription(accommodationEntity.getDescription());
        responseAccommodationDTO.setCity(accommodationEntity.getCity());
        responseAccommodationDTO.setCountry(accommodationEntity.getCountry());
        responseAccommodationDTO.setAvailability(accommodationEntity.getAvailability());
        responseAccommodationDTO.setPriceMax(accommodationEntity.getPriceMax());
        responseAccommodationDTO.setPriceMin(accommodationEntity.getPriceMin());

        return responseAccommodationDTO;
    }

    // Converter con listado de alojamientos

    public AccommodationDTO toDtoWithRooms(AccommodationEntity accommodationEntity) {
        AccommodationDTO accommodationDTO = new AccommodationDTO();
        accommodationDTO.setUuid(accommodationEntity.getUuid());
        accommodationDTO.setName(accommodationEntity.getName());
        accommodationDTO.setType(accommodationEntity.getType());
        accommodationDTO.setDescription(accommodationEntity.getDescription());
        accommodationDTO.setCity(accommodationEntity.getCity());
        accommodationDTO.setCountry(accommodationEntity.getCountry());
        accommodationDTO.setAvailability(accommodationEntity.getAvailability());
        accommodationDTO.setPriceMax(accommodationEntity.getPriceMax());
        accommodationDTO.setPriceMin(accommodationEntity.getPriceMin());
        accommodationDTO.setStatus(accommodationEntity.getStatus());

        accommodationDTO.setRooms(
                accommodationEntity.getRooms()
                                   .stream()
                                   .map(roomConverter::convertEntityToDTO)
                                   .toList()
        );

        return accommodationDTO;
    }

    // Converter para peticiones de parte de usuario

    public RequestAccommodationDTO toDtoRequest(AccommodationEntity accommodationEntity) {
        RequestAccommodationDTO requestAccommodationDTO = new RequestAccommodationDTO();
        requestAccommodationDTO.setUuid(accommodationEntity.getUuid());
        requestAccommodationDTO.setName(accommodationEntity.getName());
        requestAccommodationDTO.setType(accommodationEntity.getType());
        requestAccommodationDTO.setDescription(accommodationEntity.getDescription());
        requestAccommodationDTO.setCity(accommodationEntity.getCity());
        requestAccommodationDTO.setCountry(accommodationEntity.getCountry());


        return requestAccommodationDTO;
    }

    public AccommodationEntity toEntityRequest(RequestAccommodationDTO requestAccommodationDTO) {
        AccommodationEntity accommodationEntity = new AccommodationEntity();
        accommodationEntity.setUuid(requestAccommodationDTO.getUuid());
        accommodationEntity.setName(requestAccommodationDTO.getName());
        accommodationEntity.setType(requestAccommodationDTO.getType());
        accommodationEntity.setDescription(requestAccommodationDTO.getDescription());
        accommodationEntity.setCity(requestAccommodationDTO.getCity());
        accommodationEntity.setCountry(requestAccommodationDTO.getCountry());


        return accommodationEntity;
    }
}
