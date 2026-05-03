package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.converter.RoomConverter;
import com.Angelh0.stayhub.converter.SearchConverter;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.LastSearchEntity;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import com.Angelh0.stayhub.exception.SearchException.DateValidate;
import com.Angelh0.stayhub.repository.RoomRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.BusinessService;
import com.Angelh0.stayhub.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRoomRepository searchRoomRepository;
    private final SearchConverter searchConverter;
    private final RoomRepository roomRepository;
    private final RoomConverter roomConverter;
    private final BusinessService businessService;

    public SearchServiceImpl(SearchRoomRepository searchRoomRepository,
                             SearchConverter searchConverter,
                             RoomRepository roomRepository,
                             RoomConverter roomConverter, BusinessService businessService, AccommodationConverter accommodationConverter) {
        this.searchRoomRepository = searchRoomRepository;
        this.searchConverter = searchConverter;
        this.roomRepository = roomRepository;
        this.roomConverter = roomConverter;
        this.businessService = businessService;
    }

    @Override
    public List<ResponseAccommodationDTO> searchAdvanced(SearchRoomDTO searchRoomDTO, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut) {

        UUID uuidUser = UUID.fromString((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        DateValidate.validateCheckIn(checkIn, checkOut); //Comprobación de fechas
        saveSearch(searchRoomDTO, uuidUser, city, room, capacity, checkIn, checkOut); //Guardar última búsqueda

        // Primer filtrado
        List<RoomEntity> roomEntities = roomRepository.findByAccommodation_CityAndRoomGreaterThanEqualAndCapacityGreaterThanEqual(city, room, capacity);
        List<String> uuidList = businessService.filterRoomAvailable(roomEntities);

        // Segundo filtrado (disponibilidad)
        List<RoomEntity> availableRooms = businessService.filterCheckAvailability(uuidList, roomEntities, checkIn, checkOut);

        // Filtrado final (DTOs de alojamiento sin duplicados)
        return businessService.filterAccommodation(availableRooms);
    }


    @Override
    public List<ResponseRoomDTO> searchAdvancedRoom(UUID uuid) {

        UUID uuidUser = UUID.fromString((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        Optional<LastSearchEntity> searchRoomEntity = searchRoomRepository.findByUuidUser(uuidUser);

        if (searchRoomEntity.isPresent()) {
            LastSearchEntity search = searchRoomEntity.get();

            List<RoomEntity> roomEntities = roomRepository.findByAccommodation_CityAndRoomGreaterThanEqualAndCapacityGreaterThanEqual(search.getCity(), search.getRoom(), search.getCapacity());
            List<String> uuidList = businessService.filterRoomAvailable(roomEntities);

            List<RoomEntity> availableRooms = businessService.filterCheckAvailability(uuidList, roomEntities, search.getCheckIn(), search.getCheckOut());

            return businessService.returnRooms(availableRooms, uuid);

        }
        return null;
    }

    public void saveSearch(SearchRoomDTO searchRoomDTO, UUID uuidUser, String city, int room, int capacity, LocalDate checkIn, LocalDate checkOut) {

        LastSearchEntity lastSearch = searchRoomRepository
                .findByUuidUser(uuidUser)
                .orElse(new LastSearchEntity());

        lastSearch.setSearchUuid(UUID.randomUUID());
        lastSearch.setUuidUser(uuidUser);
        lastSearch.setCity(city);
        lastSearch.setRoom(room);
        lastSearch.setCapacity(capacity);
        lastSearch.setCheckIn(checkIn);
        lastSearch.setCheckOut(checkOut);

        searchRoomRepository.save(lastSearch);
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
    public SearchRoomDTO getLastSearch(UUID uuid) {
        Optional<LastSearchEntity> lastSearch = searchRoomRepository.findByUuidUser(uuid);

        if (lastSearch.isPresent()) {
            LastSearchEntity lastSearchEntity = lastSearch.get();

            return searchConverter.convertToDTO(lastSearchEntity);
        }

        return null;
    }

    @Override
    public SearchRoomDTO getChecks() {
        Optional<LastSearchEntity> search = searchRoomRepository.findById(1);

        if (search.isPresent()) {
            return searchConverter.convertToDTO(search.get());
        }
        return null;
    }
}