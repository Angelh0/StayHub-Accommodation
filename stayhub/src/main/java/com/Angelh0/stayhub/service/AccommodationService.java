package com.Angelh0.stayhub.service;

import com.Angelh0.stayhub.dto.AccommodationDTO;
import com.Angelh0.stayhub.dto.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;

import java.util.List;
import java.util.UUID;

public interface AccommodationService {

    RequestAccommodationDTO createAccommodation(RequestAccommodationDTO requestAccommodationDTO);
    List<ResponseAccommodationDTO> getAllAccommodations();
    ResponseAccommodationDTO modifiedAccommodation(RequestAccommodationDTO requestAccommodationDTO, UUID uuid);
    AccommodationDTO getAccommodationById(UUID uuid);
    void deleteAccommodation (UUID uuid);
    List<ResponseAccommodationDTO> getAccommodationByCity(String city);
}
