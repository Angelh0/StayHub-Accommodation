package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.converter.SearchConverter;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchResultEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.exception.SearchException.DateValidate;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchResultRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.BusinessService;
import com.Angelh0.stayhub.service.SearchService;
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
    private final RoomConverter roomConverter;;
    private final BusinessService businessService;
    private final SearchResultRepository searchResultRepository;

    public SearchServiceImpl(SearchRoomRepository searchRoomRepository,
                             SearchConverter searchConverter,
                             RoomRepository roomRepository,
                             RoomConverter roomConverter, BusinessService businessService, SearchResultRepository searchResultRepository1, AccommodationConverter accommodationConverter) {
        this.searchRoomRepository = searchRoomRepository;
        this.searchConverter = searchConverter;
        this.roomRepository = roomRepository;
        this.roomConverter = roomConverter;
        this.businessService = businessService;
        this.searchResultRepository = searchResultRepository1;
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


    @Override
    public List<ResponseRoomDTO> searchAdvancedRoom(UUID uuid) {

        businessService.updateRoomValues();

        Optional<SearchResultEntity> search = searchResultRepository.findById(1);

        List<ResponseRoomDTO> roomDTOs = new ArrayList<>();

        if (search.isPresent()) {
            List<RoomEntity> available = search.get().getRooms();

            for (RoomEntity roomEntity : available) {
                if (roomEntity.getAccommodation().getUuid().equals(uuid)) {
                    ResponseRoomDTO responseRoomDTO = roomConverter.responseRoomToDTO(roomEntity);
                    roomDTOs.add(responseRoomDTO);
                }
            }
        }

        return roomDTOs;
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
            return searchConverter.convertToDTO(room);
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
