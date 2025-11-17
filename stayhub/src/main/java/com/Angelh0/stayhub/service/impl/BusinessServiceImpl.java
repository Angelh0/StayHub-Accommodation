package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.RoomAvailabilityDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchResultEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.exception.RoomException.RoomContainsReservation;
import com.Angelh0.stayhub.grpcClient.GrpcClientFutureReservation;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchResultRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
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
    private final SearchResultRepository searchResultRepository;
    private final AccommodationRepository accommodationRepository;
    private final SearchRoomRepository searchRoomRepository;
    private final RoomRepository roomRepository;
    private final GrpcClientFutureReservation grpcClientFutureReservation;

    public BusinessServiceImpl(GrpcClientGetAvailability availabilityGrpcClient, AccommodationConverter accommodationConverter, SearchResultRepository searchResultRepository, AccommodationRepository accommodationRepository, SearchRoomRepository searchRoomRepository, RoomRepository roomRepository, GrpcClientFutureReservation grpcClientFutureReservation) {
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.accommodationConverter = accommodationConverter;
        this.searchResultRepository = searchResultRepository;
        this.accommodationRepository = accommodationRepository;
        this.searchRoomRepository = searchRoomRepository;
        this.roomRepository = roomRepository;
        this.grpcClientFutureReservation = grpcClientFutureReservation;
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

        saveSearchResult(available);
        updateAccommodationValues();

        List<ResponseAccommodationDTO> accommodation = new ArrayList<>();
        List<AccommodationEntity> accommodationEntities = accommodationRepository.findAll();

        for (AccommodationEntity accommodationEntity : accommodationEntities) {
            boolean find = false;

           for (RoomEntity room : available) {
               if (room.getAccommodation().getUuid().equals(accommodationEntity.getUuid())) {
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
    public void saveSearchResult(List<RoomEntity> availableRooms) {
        SearchResultEntity searchResultEntity = new SearchResultEntity();

        List<RoomEntity> room = new ArrayList<>();

        for (RoomEntity roomEntity : availableRooms) {
            room.add(roomEntity);
        }

        List<AccommodationEntity> accommodation = new ArrayList<>();

        for (RoomEntity roomEntity : availableRooms) {
            AccommodationEntity accommodationEntity = roomEntity.getAccommodation();

            boolean exist = false;

            for (AccommodationEntity accommodationEntities : accommodation) {
                if (accommodationEntities.getUuid().equals(accommodationEntity.getUuid())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                accommodation.add(accommodationEntity);
            }
        }

        searchResultRepository.deleteAll();

        searchResultEntity.setRooms(room);
        searchResultEntity.setAccommodation(accommodation);

        searchResultRepository.save(searchResultEntity);
        searchResultRepository.flush();
    }

    @Override
    public void updateRoomValues() {

        Optional<SearchRoomEntity> searchClient = searchRoomRepository.findById(1);

        Optional<SearchResultEntity> search = searchResultRepository.findById(1);

        if (search.isPresent() && searchClient.isPresent()) {

            LocalDate getCheckIn = searchClient.get().getCheckIn();
            LocalDate getCheckOut = searchClient.get().getCheckOut();

            long daysDiff = ChronoUnit.DAYS.between(getCheckIn, getCheckOut);

            List<RoomEntity> availableRooms = search.get().getRooms();

            for (RoomEntity roomEntity : availableRooms) {
                double price = roomEntity.getPrice();
                double totalPrice = daysDiff * price;

                roomEntity.setTotalPrice(totalPrice);
                roomRepository.save(roomEntity);
            }
        }
    }


    @Override
    public void updateAccommodationValues() {

        Double priceMax = null;
        Double priceMin = null;

        Optional<SearchResultEntity> search = searchResultRepository.findById(1);

        if (search.isPresent()) {

            List<RoomEntity> availableRooms = search.get().getRooms();

            for (AccommodationEntity accommodation : search.get().getAccommodation()) {
                int available = 0;

                for (RoomEntity room : availableRooms) {
                    if (room.getAccommodation().getUuid().equals(accommodation.getUuid())) {
                        available++;

                        if (priceMax == null || room.getPrice() > priceMax  ) {
                            priceMax = room.getPrice();
                        }
                        if (priceMin == null || room.getPrice() < priceMin) {
                            priceMin = room.getPrice();
                        }
                    }
                }

                accommodation.setPriceMax(priceMax);
                accommodation.setPriceMin(priceMin);
                accommodation.setAvailability(available);
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
            } else if (accommodation.getStatus() != AccommodationStatus.Draft){
                accommodation.setStatus(AccommodationStatus.Temporarily_closed);
            }

            accommodationRepository.save(accommodation);

        } else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
    }

    @Override
    public void validateRoomStatus(UUID uuidRoom) {

        Optional<RoomEntity> room = roomRepository.findByUuid(uuidRoom);

        if (room.isPresent()) {
            RoomAvailabilityDTO availabilityDTO = grpcClientFutureReservation.getFutureReservation(room.get().getUuid());
            if (availabilityDTO.isAvailable()) {
                throw new RoomContainsReservation("La habitacion seleccionada, contiene reservas activas posteriores a la fecha actual");
            }
        } else {
            throw new NotFoundException("No se ha encontrado ninguna habitacion con el UUID introducido");
        }
    }

}