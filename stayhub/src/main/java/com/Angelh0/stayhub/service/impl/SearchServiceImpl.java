package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.converter.SearchConverter;
import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.RoomAvailabilityDTO;
import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.exception.DateValidate;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
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


    public SearchServiceImpl(SearchRoomRepository searchRoomRepository,
                             SearchConverter searchConverter,
                             RoomRepository roomRepository,
                             RoomConverter roomConverter, GrpcClientGetAvailability availabilityGrpcClient, AccommodationConverter accommodationConverter) {
        this.searchRoomRepository = searchRoomRepository;
        this.searchConverter = searchConverter;
        this.roomRepository = roomRepository;
        this.roomConverter = roomConverter;
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.accommodationConverter = accommodationConverter;
    }


    @Override
    public List<ResponseAccommodationDTO> searchAdvanced(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut) {

        DateValidate.validateCheckIn(checkIn, checkOut); //Comprobacion de fechas

        saveSearch(searchRoomDTO, city, room, capacity, checkIn, checkOut); //Guardado de ultima busqueda

        //Primer filtrado (city, room, capacity)
        List<RoomDTO> dto = new ArrayList<>();

        List<RoomEntity> roomEntities = roomRepository.findByAccommodation_CityAndRoomAndCapacity(city, room, capacity);

        for (RoomEntity roomEntity : roomEntities) {
            dto.add(roomConverter.convertEntityToDTO(roomEntity));
        }

        //Obtencion de UUIDRoom obtenidas en primer filtrado
        List<String> uuidList = new ArrayList<>();
        for (RoomDTO roomDTO : dto) {
            uuidList.add(roomDTO.getUuid().toString());
        }

        //Segundo filtrado (CheckIn - CheckOut)
        List<RoomAvailabilityDTO> availabilityList = availabilityGrpcClient.getAvailability(
                uuidList,
                checkIn.toString(),
                checkOut.toString()
        );

        List<RoomEntity> available = new ArrayList<>();


        for (RoomAvailabilityDTO roomAvailability : availabilityList) {
            if (roomAvailability.isAvailable()) {
                for (RoomEntity roomEntity : roomEntities) {
                    if (roomEntity.getUuid().equals(roomAvailability.getUuidRoom())) {
                        available.add(roomEntity);
                        break;
                    }
                }
            }
        }

        List<ResponseAccommodationDTO> accommodation = new ArrayList<>();

        for (RoomEntity roomEntity : available) {
            AccommodationEntity accommodationEntity = roomEntity.getAccommodation();
            if (accommodationEntity != null) {
                boolean duplicate = false;
                for (ResponseAccommodationDTO exist : accommodation) {
                    if (exist.getUuid().equals(accommodationEntity.getUuid())) {
                        duplicate = true;
                        break;
                    }
                }
                if (!duplicate) {
                    ResponseAccommodationDTO response = accommodationConverter.responseToDTO(accommodationEntity);
                    accommodation.add(response);
                }
            }
        }

        return accommodation;
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
