package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.exception.AccommodationException.AccommodationContainsRoom;
import com.Angelh0.stayhub.exception.ErrorResponse;
import com.Angelh0.stayhub.exception.GlobalExceptionHandler;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.service.AccommodationService;
import com.Angelh0.stayhub.service.BusinessService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.time.LocalDateTime;
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
    public ResponseAccommodationDTO modifiedAccommodation(UpdateAccommodationDTO updateAccommodationDTO, UUID uuid) {

        Optional<AccommodationEntity> optionalAccommodation = accommodationRepository.findByUuid(uuid);

        if (optionalAccommodation.isPresent()) {
            AccommodationEntity accommodation = optionalAccommodation.get();

            if (updateAccommodationDTO.getName() != null) {
                accommodation.setName(updateAccommodationDTO.getName());
            }

            if (updateAccommodationDTO.getDescription() != null) {
                accommodation.setDescription(updateAccommodationDTO.getDescription());
            }

            accommodation = accommodationRepository.save(accommodation);

            return accommodationConverter.responseToDTO(accommodation);
        }

        else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
    }


    @Override
    @Transactional
    public void deleteAccommodation(UUID uuid){

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuid);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            if (!accommodationEntity.getRooms().isEmpty()) {
                throw new AccommodationContainsRoom("No se puede eliminar un alojamiento sin borrar previamente sus habitaciones");
            } else{
                accommodationRepository.deleteByUuid(uuid);
            }
        } else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
     }

    @Override
    public AccommodationDTO getAccommodationById(UUID uuid) {

        Optional<AccommodationEntity> entity = accommodationRepository.findByUuid(uuid);

        if (entity.isPresent()) {
            businessService.updateValues(entity.get());
            AccommodationDTO dto = accommodationConverter.toDtoWithRooms(entity.get());
                    return dto;
        } else {
            throw new NotFoundException("No se ha encontrado el UUID introducido");
        }
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