package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.dto.room.*;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.Angelh0.stayhub.exception.InvalidValues;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.exception.RoomException.RoomContainsReservation;
import com.Angelh0.stayhub.exception.SearchException.DateValid;
import com.Angelh0.stayhub.exception.SearchException.DateValidate;
import com.Angelh0.stayhub.grpcClient.GrpcClientFutureReservation;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.service.AccommodationDraftService;
import com.Angelh0.stayhub.service.BusinessService;
import com.Angelh0.stayhub.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Angelh0.stayhub.entity.AccommodationEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private GrpcClientFutureReservation grpcClientFutureReservation;

    @Autowired
    private AccommodationDraftService accommodationDraftService;


    @Override
    public RoomDTO createRoom(RoomDTO roomDTO, UUID uuid) {
        Optional<AccommodationEntity> accommodationEntity = accommodationRepository.findByUuid(uuid);

        if (accommodationEntity.isPresent()) {
            AccommodationEntity accommodation = accommodationEntity.get();
            RoomEntity room = roomConverter.convertDTOtoEntity(roomDTO);
            room.setAccommodation(accommodation);
            room = roomRepository.save(room);
            accommodation.getRooms().add(room);
            businessService.updateValues(accommodation);
            room = roomRepository.save(room);
            roomDTO = roomConverter.convertEntityToDTO(room);
            businessService.validateAccommodationStatus(roomDTO.getUuid());
            accommodationDraftService.checkAddRooms(accommodation.getUuid());
            return roomDTO;
        }
        return null;
    }


    @Override
    public List<RoomAdminDTO> getAllRooms() {

        List<RoomAdminDTO> dto = new ArrayList<>();
        List<RoomEntity> entity = (List<RoomEntity>) roomRepository.findAll();

        for (RoomEntity roomEntity : entity) {
            dto.add(roomConverter.adminConverterToDTO(roomEntity));
        }
        return dto;
    }

    @Override
    public RoomDTO modifiedRooms(UpdateRoomDTO updateRoomDTO, UUID uuid) {

        Optional<RoomEntity> roomEntity = roomRepository.findByUuid(uuid);

        if (roomEntity.isPresent()) {
            RoomEntity entity = roomEntity.get();

            if (updateRoomDTO.getCapacity() != 0) {
                entity.setCapacity(updateRoomDTO.getCapacity());
            }

            if (updateRoomDTO.getBeds() != 0) {
                entity.setBeds(updateRoomDTO.getBeds());
            }

            if (updateRoomDTO.getPrice() != 0) {
                entity.setPrice(updateRoomDTO.getPrice());
            }

            if (updateRoomDTO.getRoomStatus() != RoomStatus.Active) {
                if (updateRoomDTO.getBlockStartDate() == null || updateRoomDTO.getBlockEndDate() == null) {
                    throw new NotFoundException("Los bloqueos deben contener una fecha de inicio y de fin");
                }
                DateValidate.validateBlockDate(updateRoomDTO.getBlockStartDate(), updateRoomDTO.getBlockEndDate());
                businessService.validateRoomStatus(uuid, String.valueOf(updateRoomDTO.getBlockStartDate()), String.valueOf(updateRoomDTO.getBlockEndDate()));
                businessService.validateAccommodationStatus(uuid);
            }

            if (updateRoomDTO.getPhotos() != null){
                updateRoomDTO.setPhotos(updateRoomDTO.getPhotos());
                updateRoomDTO.setPhotos(updateRoomDTO.getPhotos());
            }

            roomRepository.save(entity);

            return roomConverter.convertEntityToDTO(entity);
        } throw new NotFoundException("No se ha encontrado ninguna habitacion con el UUID introducido");
    }

    @Override
    public ResponseRoomDTO getRooms(UUID uuid) {

        Optional<RoomEntity> roomEntity = roomRepository.findByUuid(uuid);

        if (roomEntity.isPresent()) {
            RoomEntity room = roomEntity.get();

            return roomConverter.responseRoomToDTO(room);
        }
        throw new NotFoundException("No se ha encontrado ninguna habitacion con el UUID introducido");
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {

        Optional<RoomEntity> room = roomRepository.findByUuid(uuid);

        if (room.isPresent()) {
            RoomAvailabilityDTO availabilityDTO = grpcClientFutureReservation.getFutureReservation(room.get().getUuid());
            if (availabilityDTO.isAvailable()) {
                throw new RoomContainsReservation("La habitacion seleccionada, contiene reservas activas posteriores a la fecha actual.");
            }
            roomRepository.deleteByUuid(uuid);
        } else {
            throw new NotFoundException("No se ha encontrado ninguna habitacion con el UUID introducido");
        }
    }
}