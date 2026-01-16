package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.Angelh0.stayhub.entity.*;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.exception.RoomException.RoomContainsReservation;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.grpcClient.GrpcClientRoomStatusChange;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.AccommodationDraftService;
import com.Angelh0.stayhub.service.BusinessService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final GrpcClientGetAvailability availabilityGrpcClient;

    private final AccommodationConverter accommodationConverter;
    private final AccommodationRepository accommodationRepository;
    private final SearchRoomRepository searchRoomRepository;
    private final RoomRepository roomRepository;
    private final AccommodationDraftService accommodationDraftService;
    private final GrpcClientRoomStatusChange grpRoomStatusChange;
    private final RoomConverter roomConverter;


    public BusinessServiceImpl(GrpcClientGetAvailability availabilityGrpcClient, AccommodationConverter accommodationConverter, AccommodationRepository accommodationRepository, SearchRoomRepository searchRoomRepository, RoomRepository roomRepository, AccommodationDraftService accommodationDraftService, GrpcClientRoomStatusChange roomStatusChange, GrpcClientRoomStatusChange grpRoomStatusChange, RoomConverter roomConverter, RoomConverter roomConverter1) {
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.accommodationConverter = accommodationConverter;
        this.accommodationRepository = accommodationRepository;
        this.searchRoomRepository = searchRoomRepository;
        this.roomRepository = roomRepository;
        this.accommodationDraftService = accommodationDraftService;
        this.grpRoomStatusChange = grpRoomStatusChange;

        this.roomConverter = roomConverter1;
    }

    @Override
    public List<String> filterRoomAvailable(List<RoomEntity> roomEntities) {
        List<String> uuidList = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            uuidList.add(roomEntity.getUuid().toString());
        }
        return uuidList;
    }

    public List<RoomEntity> filterCheckAvailability(List<String> uuidList, List<RoomEntity> roomEntities, LocalDate checkIn, LocalDate checkOut) {
        List<RoomAvailabilityDTO> availabilityList = availabilityGrpcClient.getAvailability(
                uuidList,
                checkIn.toString(),
                checkOut.toString()
        );

        List<RoomEntity> available = new ArrayList<>();
        for (RoomAvailabilityDTO roomAvailability : availabilityList) {
            if (roomAvailability.isAvailable()) {
                for (RoomEntity roomEntity : roomEntities) {
                    if (roomEntity.getUuid().equals(roomAvailability.getUuidRoom()) && roomEntity.getRoomStatus() == RoomStatus.Active) {
                        available.add(roomEntity);
                        break;
                    }
                }
            }
        }
        return available;
    }

    public List<ResponseAccommodationDTO> filterAccommodation(List<RoomEntity> available) {

        updateAccommodationValues(available);

        List<ResponseAccommodationDTO> accommodation = new ArrayList<>();
        List<AccommodationEntity> accommodationEntities = accommodationRepository.findAll();

        for (AccommodationEntity accommodationEntity : accommodationEntities) {
            boolean find = false;

            for (RoomEntity room : available) {
                accommodationDraftService.checkPublishAccommodation(room.getAccommodation().getUuid());
                boolean checkStay = accommodationDraftService.checkStayAccommodation(room.getAccommodation().getUuid());
                boolean checkMonthAvailable = accommodationDraftService.checkMonthAvailability(room.getAccommodation().getUuid());
                if (room.getAccommodation().getUuid().equals(accommodationEntity.getUuid()) && accommodationEntity.getStatus() == AccommodationStatus.Active && checkStay && checkMonthAvailable) {
                    find = true;
                    break;
                }
            }
            if (find) {
                accommodation.add(accommodationConverter.responseToDTO(accommodationEntity));
            }
        }

        return accommodation;
    }

    @Override
    public List<ResponseRoomDTO> returnRooms(List<RoomEntity> available, UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            List<ResponseRoomDTO> rooms = new ArrayList<>();

            for (RoomEntity room : available) {
                if (room.getAccommodation().getUuid().equals(accommodationEntity.getUuid())) {
                    updateRoomValues(room.getUuid());
                    rooms.add(roomConverter.responseRoomToDTO(room));
                }
            }
            return rooms;
        }
        throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
    }


    @Override
    public void updateRoomValues(UUID uuidRoom) {

        Optional<SearchRoomEntity> searchClient = searchRoomRepository.findById(1);
        Optional<RoomEntity> room = roomRepository.findByUuid(uuidRoom);

        if (searchClient.isPresent() && room.isPresent()) {
            RoomEntity roomEntity = room.get();

            LocalDate getCheckIn = searchClient.get().getCheckIn();
            LocalDate getCheckOut = searchClient.get().getCheckOut();

            long daysDiff = ChronoUnit.DAYS.between(getCheckIn, getCheckOut);

            double price = roomEntity.getPrice();
            double totalPrice = daysDiff * price;

            roomEntity.setTotalPrice(totalPrice);
            roomRepository.save(roomEntity);
        }
    }


    @Override
    public void updateAccommodationValues(List<RoomEntity> availableRooms) {

        List<UUID> listAccommodation = new ArrayList<>();

        for (RoomEntity room : availableRooms) {
            AccommodationEntity accommodation = room.getAccommodation();
            UUID accommodationUUID = accommodation.getUuid();

            if (!listAccommodation.contains(accommodationUUID)) {
                listAccommodation.add(accommodationUUID);

                int availableCount = 0;
                Double priceMax = null;
                Double priceMin = null;

                for (RoomEntity r : availableRooms) {
                    if (r.getAccommodation().getUuid().equals(accommodationUUID)) {
                        availableCount++;

                        if (priceMax == null || r.getPrice() > priceMax) {
                            priceMax = r.getPrice();
                        }
                        if (priceMin == null || r.getPrice() < priceMin) {
                            priceMin = r.getPrice();
                        }
                    }
                }

                accommodation.setAvailability(availableCount);
                accommodation.setPriceMax(priceMax);
                accommodation.setPriceMin(priceMin);
                accommodationRepository.saveAndFlush(accommodation);
            }
        }
    }

    @Override
    public AccommodationEntity updateValues(AccommodationEntity accommodationEntity) {

        Double priceMax = null;
        Double priceMin = null;
        int contador = 0;

        List<RoomEntity> roomEntity = accommodationEntity.getRooms();

        for (RoomEntity room : roomEntity) {

            if (room.getStatus() == StatusType.Available) {
                contador++;

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

    @Transactional
    @Override
    public void validateAccommodationStatus(UUID uuidRoom) {

        Optional<RoomEntity> room = roomRepository.findByUuid(uuidRoom);

        if (room.isPresent()) {
            boolean active = false;
            RoomEntity roomEntity = room.get();
            AccommodationEntity accommodation = roomEntity.getAccommodation();

            for (RoomEntity rooms : accommodation.getRooms()) {
                if ((rooms.getRoomStatus() == RoomStatus.Active)) {
                    active = true;
                    break;
                }
            }

            if (active) {
                if (accommodation.getStatus() != AccommodationStatus.Draft) {
                    accommodation.setStatus(AccommodationStatus.Active);
                }
            } else if (accommodation.getStatus() != AccommodationStatus.Draft) {
                accommodation.setStatus(AccommodationStatus.Closed);
            }

            accommodationRepository.save(accommodation);

        } else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
    }

    @Override
    public void validateRoomStatus(UUID uuidRoom, String blockStartDate, String blockEndDate) {

        Optional<RoomEntity> room = roomRepository.findByUuid(uuidRoom);

        if (room.isPresent()) {
            RoomAvailabilityDTO availabilityDTO = grpRoomStatusChange.checkStatusRoom(uuidRoom, blockStartDate, blockEndDate);
            if (!availabilityDTO.isAvailable()) {
                throw new RoomContainsReservation(availabilityDTO.getMessage());
            }
        } else {
            throw new NotFoundException("No se ha encontrado ninguna habitacion con el UUID introducido");
        }
    }
}