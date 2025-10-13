package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.converter.SearchConverter;
import com.Angelh0.stayhub.dto.*;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.exception.DateValidate;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.BusinessService;
import com.Angelh0.stayhub.service.SearchService;
import com.checkAvailability.grpc.AvailabilityRequest;
import com.checkAvailability.grpc.AvailabilityResponse;
import com.checkAvailability.grpc.RoomAvailability;
import com.checkAvailability.grpc.availabilityServiceGrpc;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRoomRepository searchRoomRepository;
    private final SearchConverter searchConverter;
    private final RoomRepository roomRepository;
    private final RoomConverter roomConverter;
    private final GrpcClientGetAvailability availabilityGrpcClient;
    private final AccommodationConverter accommodationConverter;
    private final BusinessService businessService;


    public SearchServiceImpl(SearchRoomRepository searchRoomRepository,
                             SearchConverter searchConverter,
                             RoomRepository roomRepository,
                             RoomConverter roomConverter, GrpcClientGetAvailability availabilityGrpcClient, AccommodationConverter accommodationConverter, BusinessService businessService) {
        this.searchRoomRepository = searchRoomRepository;
        this.searchConverter = searchConverter;
        this.roomRepository = roomRepository;
        this.roomConverter = roomConverter;
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.accommodationConverter = accommodationConverter;
        this.businessService = businessService;
    }


    @Override
    public List<ResponseAccommodationDTO> searchAdvanced(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut) {

        DateValidate.validateCheckIn(checkIn, checkOut); //Comprobación de fechas
        saveSearch(searchRoomDTO, city, room, capacity, checkIn, checkOut); //Guardar última búsqueda

        // Primer filtrado
        List<RoomEntity> roomEntities = roomRepository.findByAccommodation_CityAndRoomAndCapacity(city, room, capacity);
        List<String> uuidList = businessService.filterRoomAvailable(roomEntities);

        // Segundo filtrado (disponibilidad)
        List<RoomEntity> availableRooms = businessService.filterCheckAvailability(uuidList, roomEntities, checkIn, checkOut);

        // Filtrado final (DTOs de alojamiento sin duplicados)
        return businessService.filterAccommodation(availableRooms);
    }



    public void saveSearch(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut) {
        SearchRoomEntity searchRoomEntity = new SearchRoomEntity();

        searchRoomEntity.setSearchUuid(UUID.randomUUID());

        searchRoomEntity.setCity(city);
        searchRoomEntity.setRoom(room);
        searchRoomEntity.setCapacity(capacity);
        searchRoomEntity.setCheckIn(checkIn);
        searchRoomEntity.setCheckOut(checkOut);


        searchRoomRepository.deleteAll();
        searchRoomRepository.save(searchRoomEntity);
    }

    @Override
    public SearchRoomDTO searchGetRooms(UUID uuid) {
        Optional<RoomEntity> roomEntity = roomRepository.findByUuid(uuid);

        if (roomEntity.isPresent()) {
            RoomEntity room = roomEntity.get();
            if (room.getStatus() == StatusType.Disable) {
                return null;
            }
            return roomConverter.convertToDTO(room);
        }
        return null;
    }

    @Override
    public SearchRoomDTO getChecks() {
        Optional<SearchRoomEntity> search = searchRoomRepository.findById(1);

        if (search.isPresent()) {
            return searchConverter.convertToDTO(search.get());
        }
        return null;
    }
}
