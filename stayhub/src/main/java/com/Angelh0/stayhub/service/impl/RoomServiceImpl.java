package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.converter.SearchConverter;
import com.Angelh0.stayhub.dto.RoomAdminDTO;
import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.BusinessService;
import com.Angelh0.stayhub.service.RoomService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Angelh0.stayhub.entity.AccommodationEntity;


import java.time.LocalDate;
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
    private SearchRoomRepository searchRoomRepository;

    @Autowired
    private SearchConverter searchConverter;


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
    public RoomDTO modifiedRooms(RoomDTO roomDTO, UUID uuid) {

        Optional<RoomEntity> roomEntity = roomRepository.findByUuid(uuid);

        if (roomEntity.isPresent()) {
            RoomEntity entity = roomEntity.get();

            if (roomDTO.getRoom() != 0) {
                entity.setRoom(roomDTO.getRoom());
            }

            if (roomDTO.getCapacity() != 0) {
                entity.setCapacity(roomDTO.getCapacity());
            }

            if (roomDTO.getBeds() != 0) {
                entity.setBeds(roomDTO.getBeds());
            }

            if (roomDTO.getType() != null) {
                entity.setType(roomDTO.getType());
            }

            if (roomDTO.getPrice() != 0) {
                entity.setPrice(roomDTO.getPrice());
            }

            if (roomDTO.getAreaInSquareMeters() != 0) {
                entity.setAreaInSquareMeters(roomDTO.getAreaInSquareMeters());
            }

            if (roomDTO.getStatus() != null) {
                entity.setStatus(roomDTO.getStatus());
            }

            roomRepository.save(entity);

            return roomConverter.convertEntityToDTO(entity);
        }
        return null;
    }

    @Override
    public RoomDTO getRooms(UUID uuid) {

        Optional<RoomEntity> roomEntity = roomRepository.findByUuid(uuid);

        if (roomEntity.isPresent()) {
            RoomEntity room = roomEntity.get();
            if (room.getStatus() == StatusType.Disable) {
                return null;
            }
            return roomConverter.convertEntityToDTO(room);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {

        Optional<RoomEntity> room = roomRepository.findByUuid(uuid);

        if (room.isPresent()) {
            roomRepository.deleteByUuid(uuid);
        }
    }


}