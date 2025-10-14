package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.RoomAvailabilityDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchResultEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.SearchResultRepository;
import com.Angelh0.stayhub.service.BusinessService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

    private final GrpcClientGetAvailability availabilityGrpcClient;


    private final AccommodationConverter accommodationConverter;
    private final SearchResultRepository searchResultRepository;
    private final AccommodationRepository accommodationRepository;

    public BusinessServiceImpl(GrpcClientGetAvailability availabilityGrpcClient, AccommodationConverter accommodationConverter, SearchResultRepository searchResultRepository, AccommodationRepository accommodationRepository) {
        this.availabilityGrpcClient = availabilityGrpcClient;
        this.accommodationConverter = accommodationConverter;
        this.searchResultRepository = searchResultRepository;
        this.accommodationRepository = accommodationRepository;
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
                    if (roomEntity.getUuid().equals(roomAvailability.getUuidRoom())) {
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
}
