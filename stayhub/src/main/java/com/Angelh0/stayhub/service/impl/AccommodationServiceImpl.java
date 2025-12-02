package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationCalendarConverter;
import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.dto.DraftAccommodation.AccommodationCalendarDTO;
import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.UpdateAccommodationDTO;
import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.entity.AccommodationCalendarEntity;
import com.Angelh0.stayhub.entity.AccommodationDraftEntity;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.exception.AccommodationException.AccommodationContainsRoom;
import com.Angelh0.stayhub.exception.AccommodationException.AccommodationEmptyValues;
import com.Angelh0.stayhub.exception.InvalidValues;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.grpcClient.GrpcClientValidateCountry;
import com.Angelh0.stayhub.repository.AccommodationCalendarRepository;
import com.Angelh0.stayhub.repository.AccommodationDraftRepository;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.service.AccommodationDraftService;
import com.Angelh0.stayhub.service.AccommodationService;
import com.Angelh0.stayhub.service.BusinessService;
import com.checkAvailability.grpc.RoomAvailability;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    @Autowired
    private AccommodationConverter accommodationConverter;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private AccommodationDraftService accommodationDraftService;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private GrpcClientValidateCountry grpcClientValidateCountry;
    @Autowired
    private AccommodationDraftRepository accommodationDraftRepository;
    @Autowired
    private AccommodationCalendarConverter accommodationCalendarConverter;
    @Autowired
    private AccommodationCalendarRepository accommodationCalendarRepository;

    @Override
    public RequestAccommodationDTO createAccommodation(RequestAccommodationDTO requestAccommodationDTO) {

        AccommodationEntity accommodationEntity = accommodationConverter.toEntityRequest(requestAccommodationDTO);
        RoomAvailabilityDTO validate = grpcClientValidateCountry.validateValues(requestAccommodationDTO.getCity(), requestAccommodationDTO.getCountry());
        if (!validate.isAvailable()) {
            throw new NotFoundException(validate.getMessage());
        }
        accommodationEntity = accommodationRepository.save(accommodationEntity);
        requestAccommodationDTO = accommodationConverter.toDtoRequest(accommodationEntity);
        accommodationDraftService.checkBasicCreate(requestAccommodationDTO.getUuid());
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
                if (updateAccommodationDTO.getName().trim().isEmpty()) {
                    throw new AccommodationEmptyValues("El nombre no puede estar vacio");
                }
                accommodation.setName(updateAccommodationDTO.getName());
            }

            if (updateAccommodationDTO.getDescription() != null) {
                if (updateAccommodationDTO.getDescription().trim().isEmpty()) {
                    throw new AccommodationEmptyValues("La descripcion no puede estar vacia");
                }
                accommodation.setDescription(updateAccommodationDTO.getDescription());
            }

            if (updateAccommodationDTO.getMinStay() != null) {
                if (updateAccommodationDTO.getMinStay() != 0) {
                    accommodation.setMinStay(updateAccommodationDTO.getMinStay());
                }
                if (updateAccommodationDTO.getMinStay() == 0) {
                    throw new InvalidValues("La estancia minima no puede ser inferior a 1");
                }
            }

            if (updateAccommodationDTO.getMaxStay() < accommodation.getMinStay()) {
                if (updateAccommodationDTO.getMaxStay() > accommodation.getMinStay()) {
                    accommodation.setMaxStay(updateAccommodationDTO.getMaxStay());
                }
                throw new AccommodationEmptyValues("La estancia maxima no puede ser inferior a la estancia minima");
            }

            if (updateAccommodationDTO.getCalendar() != null) {
                AccommodationCalendarDTO calendarDTO = updateAccommodationDTO.getCalendar();

                if (calendarDTO.getCalendarMonth() == null || calendarDTO.getCalendarMonth().isEmpty()) {
                    throw new InvalidValues("El calendario debe contener mínimo 1 mes de disponibilidad");
                }

                if (accommodation.getCalendar() == null) {
                    AccommodationCalendarEntity newCalendar = new AccommodationCalendarEntity();
                    newCalendar.setAccommodation(accommodation);
                    accommodation.setCalendar(newCalendar);
                }

                accommodation.getCalendar().setCalendarMonth(calendarDTO.getCalendarMonth());

                accommodationCalendarRepository.save(accommodation.getCalendar());
                accommodationRepository.save(accommodation);
            }

            if (updateAccommodationDTO.getPhotos() != null) {

                if (updateAccommodationDTO.getPhotos().isEmpty()) {
                    throw new InvalidValues("El alojamiento debe contener minimo 1 foto");
                }
                if (!updateAccommodationDTO.getPhotos().isEmpty()) {
                    updateAccommodationDTO.setPhotos(updateAccommodationDTO.getPhotos());
                }
                accommodation.setPhotos(updateAccommodationDTO.getPhotos());
            }

            accommodation = accommodationRepository.save(accommodation);

            return accommodationConverter.responseToDTO(accommodation);
        }
        throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
    }


    @Override
    @Transactional
    public void deleteAccommodation(UUID uuid) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuid);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            if (!accommodationEntity.getRooms().isEmpty()) {
                throw new AccommodationContainsRoom("No se puede eliminar un alojamiento sin borrar previamente sus habitaciones");
            } else {
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

    @Override
    public List<RoomDTO> getRooms(List<RoomEntity> rooms) {
        List<RoomDTO> result = new ArrayList<>();

        for (RoomEntity room : rooms) {
            result.add(roomConverter.convertEntityToDTO(room));
        }
        return result;
    }


}