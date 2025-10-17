package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.service.AccommodationService;
import com.Angelh0.stayhub.service.BusinessService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    @Autowired
    private AccommodationConverter accommodationConverter;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private BusinessService businessService;

    @Override
    public RequestAccommodationDTO createAccommodation(RequestAccommodationDTO requestAccommodationDTO) {

        AccommodationEntity accommodationEntity = accommodationConverter.toEntityRequest(requestAccommodationDTO);
        accommodationEntity = accommodationRepository.save(accommodationEntity);
        requestAccommodationDTO = accommodationConverter.toDtoRequest(accommodationEntity);
        return requestAccommodationDTO;
    }

    @Override
    public List<ResponseAccommodationDTO> getAllAccommodations() {

        // No se modifica nada ya que tiene que devolver todos los valores

        List<ResponseAccommodationDTO> dto = new ArrayList<>();
        List<AccommodationEntity> entities = (List<AccommodationEntity>) accommodationRepository.findAll();

        for (AccommodationEntity accommodation : entities) {
            businessService.updateValues(accommodation);
            dto.add(accommodationConverter.responseToDTO(accommodation));
        }
        return dto;
    }

    @Override
    public ResponseAccommodationDTO modifiedAccommodation(RequestAccommodationDTO requestAccommodationDTO, UUID uuid) {

        Optional<AccommodationEntity> optionalAccommodation = accommodationRepository.findByUuid(uuid);

        if (optionalAccommodation.isPresent()) {
            AccommodationEntity accommodation = optionalAccommodation.get();

            if (requestAccommodationDTO.getName() != null) {
                accommodation.setName(requestAccommodationDTO.getName());
            }
            if (requestAccommodationDTO.getType() != null) {
                accommodation.setType(requestAccommodationDTO.getType());
            }
            if (requestAccommodationDTO.getDescription() != null) {
                accommodation.setDescription(requestAccommodationDTO.getDescription());
            }
            if (requestAccommodationDTO.getCity() != null) {
                accommodation.setCity(requestAccommodationDTO.getCity());
            }
            if (requestAccommodationDTO.getCountry() != null) {
                accommodation.setCountry(requestAccommodationDTO.getCountry());
            }

            accommodation = accommodationRepository.save(accommodation);

            return accommodationConverter.responseToDTO(accommodation);
        }

        return null;
    }


    @Override
    @Transactional
    public void deleteAccommodation(UUID uuid) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuid);
        String comment;

        if (accommodation.isPresent()) {
            accommodationRepository.deleteByUuid(uuid);
        }
    }

    @Override
    public AccommodationDTO getAccommodationById(UUID uuid) {

        Optional<AccommodationEntity> entity = accommodationRepository.findByUuid(uuid);

        if (entity.isPresent()) {
            businessService.updateValues(entity.get());
            AccommodationDTO dto = accommodationConverter.toDtoWithRooms(entity.get());
                    return dto;
        }
        return null;
    }

    @Override
    public List<ResponseAccommodationDTO> getAccommodationByCity(String city) {

        List<ResponseAccommodationDTO> accDTO = new ArrayList<>();
        List<AccommodationEntity> accEntity = accommodationRepository.findByCity(city);

        for (AccommodationEntity accommodationEntity : accEntity) {
            accDTO.add(accommodationConverter.responseToDTO(accommodationEntity));
        }

        return accDTO;
    }

}