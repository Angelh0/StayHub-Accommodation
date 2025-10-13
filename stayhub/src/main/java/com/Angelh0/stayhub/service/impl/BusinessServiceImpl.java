package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.RoomAvailabilityDTO;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.entity.SearchResult;
import com.Angelh0.stayhub.enums.StatusType;
import com.Angelh0.stayhub.grpcClient.GrpcClientGetAvailability;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.SearchResultRepository;
import com.Angelh0.stayhub.service.BusinessService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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

        saveSearchResult(available, accommodation);

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

    public void saveSearchResult(List<RoomEntity> availableRooms, List<ResponseAccommodationDTO> accommodationDTOs) {
        SearchResult searchResult = new SearchResult();

        List<RoomEntity> room = new ArrayList<>();

        for (RoomEntity roomEntity : availableRooms) {
            room.add(roomEntity);
        }

        List<AccommodationEntity> accommodation = new ArrayList<>();

        for (ResponseAccommodationDTO responseAccommodationDTO : accommodationDTOs) {
            UUID accommodationUuid = responseAccommodationDTO.getUuid();
            List<AccommodationEntity> accommodationEntities = accommodationRepository.uuid(accommodationUuid);

            accommodation.addAll(accommodationEntities);
        }

        searchResultRepository.deleteAll();

        searchResult.setRooms(room);
        searchResult.setAccommodation(accommodation);

        System.out.println(searchResult);

        searchResultRepository.save(searchResult);
    }



    @Override
    public AccommodationEntity updateAccommodationValues(AccommodationEntity accommodationEntity, List<ResponseAccommodationDTO> accommodation, List<RoomEntity> available) {
        return null;
    }
}
