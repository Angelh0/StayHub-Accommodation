package com.Angelh0.stayhub.service.impl;

import com.Angelh0.stayhub.converter.AccommodationConverter;
import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.entity.AccommodationCalendarEntity;
import com.Angelh0.stayhub.entity.AccommodationDraftEntity;
import com.Angelh0.stayhub.entity.AccommodationEntity;
import com.Angelh0.stayhub.entity.SearchRoomEntity;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import com.Angelh0.stayhub.exception.InvalidValues;
import com.Angelh0.stayhub.exception.NotFoundException;
import com.Angelh0.stayhub.repository.AccommodationCalendarRepository;
import com.Angelh0.stayhub.repository.AccommodationDraftRepository;
import com.Angelh0.stayhub.repository.AccommodationRepository;
import com.Angelh0.stayhub.repository.SearchRoomRepository;
import com.Angelh0.stayhub.service.AccommodationDraftService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccommodationDraftServiceImpl implements AccommodationDraftService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationDraftRepository accommodationDraftRepository;
    private final AccommodationConverter accommodationConverter;
    private final AccommodationCalendarRepository accommodationCalendarRepository;
    private final SearchRoomRepository searchRoomRepository;

    public AccommodationDraftServiceImpl(AccommodationRepository accommodationRepository, AccommodationDraftRepository accommodationDraftRepository, AccommodationConverter accommodationConverter, AccommodationCalendarRepository accommodationCalendarRepository, SearchRoomRepository searchRoomRepository) {
        this.accommodationRepository = accommodationRepository;
        this.accommodationDraftRepository = accommodationDraftRepository;
        this.accommodationConverter = accommodationConverter;
        this.accommodationCalendarRepository = accommodationCalendarRepository;
        this.searchRoomRepository = searchRoomRepository;
    }

    @Override
    public boolean checkBasicCreate(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isEmpty()) {
            return false;
        }

        AccommodationEntity accommodationEntity = accommodation.get();

        AccommodationDraftEntity accommodationDraft = accommodation.get().getDraft();

        if (accommodationDraft == null) {
            accommodationDraft = new AccommodationDraftEntity();
            accommodationDraft.setAccommodation(accommodationEntity);
            accommodationEntity.setDraft(accommodationDraft);
        }

        accommodationDraft.setBasicCreate(true);
        accommodationDraftRepository.save(accommodationDraft);
        return true;
    }

    @Override
    public AccommodationDTO stayConfiguration(UUID uuidAccommodation, AccommodationDTO accommodationDTO) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            if (accommodationDTO.getMinStay() == 0) {
                throw new InvalidValues("La estancia minima no puede ser inferior a 1 noche");
            }
            accommodationEntity.setMinStay(accommodationDTO.getMinStay());

            if (accommodationDTO.getMaxStay() < accommodationDTO.getMinStay() || accommodationDTO.getMaxStay() <= 1) {
                throw new InvalidValues("La estancia maxima debe ser superior a la estancia minima");
            }
            accommodationEntity.setMaxStay(accommodationDTO.getMaxStay());

            accommodationRepository.save(accommodationEntity);

            AccommodationDraftEntity accommodationDraft = accommodation.get().getDraft();
            accommodationDraft.setStayCustomer(true);

            accommodationDraftRepository.save(accommodationDraft);

            return accommodationConverter.toDtoWithRooms(accommodationEntity);

        } else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
    }

    @Override
    public boolean checkAddRooms(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isEmpty()) {
            return false;
        }

        AccommodationEntity accommodationEntity = accommodation.get();

        if (!accommodationEntity.getRooms().isEmpty()) {
            AccommodationDraftEntity accommodationDraft = accommodationEntity.getDraft();
            accommodationDraft.setAddRooms(true);
            accommodationDraftRepository.save(accommodationDraft);
        }
        return true;
    }

    @Override
    public AccommodationDTO availabilityCalendar(UUID uuidAccommodation, AccommodationDTO accommodationDTO) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();
            AccommodationCalendarEntity calendar = accommodationEntity.getCalendar();

            calendar.setCalendarMonth(accommodationDTO.getAvailabilityCalendar().getCalendarMonth());

            if (accommodationDTO.getAvailabilityCalendar().getCalendarMonth().isEmpty()) {
                throw new InvalidValues("El calendario debe contener minimo 1 mes de disponibilidad");
            }

            accommodationCalendarRepository.save(calendar);
            accommodationRepository.save(accommodationEntity);

            accommodationDTO = accommodationConverter.toDtoWithRooms(accommodationEntity);

            checkAvailabilityCalendar(uuidAccommodation);

            return accommodationDTO;
        } else {
            throw new NotFoundException("No se ha encontrado nigun alojamiento con el UUID introducido");
        }
    }

    @Override
    public boolean checkAvailabilityCalendar(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isEmpty()) {
            return false;
        }

        AccommodationEntity accommodationEntity = accommodation.get();

        List<Integer> calendar = accommodationEntity.getCalendar().getCalendarMonth();

        for (Integer month : calendar) {
            if (month >= 1) {
                AccommodationDraftEntity accommodationDraft = accommodationEntity.getDraft();
                accommodationDraft.setAvailabilityCalendar(true);
                accommodationDraftRepository.save(accommodationDraft);
            }
        }
        return true;
    }

    @Override
    public AccommodationDTO addPhotos(UUID uuidAccommodation, AccommodationDTO accommodationDTO) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            if (accommodationDTO.getPhotos().isEmpty()) {
                throw new InvalidValues("Debe haber minimo una foto para este alojamiento");
            }
            accommodationEntity.setPhotos(accommodationDTO.getPhotos());

            accommodationDTO = accommodationConverter.toDtoWithRooms(accommodationEntity);
            accommodationRepository.save(accommodationEntity);
            checkAddPhotos(uuidAccommodation);
            return accommodationDTO;

        } else {
            throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
        }
    }

    @Override
    public boolean checkAddPhotos(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        if (accommodation.isEmpty()) {
            return false;
        }

        AccommodationEntity accommodationEntity = accommodation.get();

        if (accommodationEntity.getPhotos() != null) {
            AccommodationDraftEntity accommodationDraft = accommodationEntity.getDraft();
            accommodationDraft.setAddPhotos(true);
            accommodationDraftRepository.save(accommodationDraft);
        }
        return true;
    }

    @Override
    public AccommodationDTO publishAccommodation(UUID uuid) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuid);

        if (accommodation.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();

            AccommodationDraftEntity accommodationDraft = accommodationEntity.getDraft();

            if (accommodationDraft.isBasicCreate() &&
                    accommodationDraft.isStayCustomer() &&
                    accommodationDraft.isAddRooms() &&
                    accommodationDraft.isAvailabilityCalendar() &&
                    accommodationDraft.isAddPhotos()) {

                accommodationDraft.setPublish(true);
                accommodationDraftRepository.save(accommodationDraft);
                accommodationEntity.setStatus(AccommodationStatus.Active);

                accommodationRepository.save(accommodationEntity);
            }
            return accommodationConverter.toDtoWithRooms(accommodationEntity);
        }
        throw new NotFoundException("No se ha encontrado ningun alojamiento con el UUID introducido");
    }

    @Override
    public boolean checkPublishAccommodation(UUID uuid) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuid);

        if (accommodation.isEmpty()) {
            return false;
        }

        AccommodationEntity accommodationEntity = accommodation.get();

        AccommodationDraftEntity accommodationDraft = accommodationEntity.getDraft();

        if (accommodationDraft.isBasicCreate() &&
                accommodationDraft.isStayCustomer() &&
                accommodationDraft.isAddRooms() &&
                accommodationDraft.isAvailabilityCalendar() &&
                accommodationDraft.isAddPhotos()) {

            accommodationDraft.setPublish(true);
            accommodationDraftRepository.save(accommodationDraft);
            accommodationEntity.setStatus(AccommodationStatus.Active);

            accommodationRepository.save(accommodationEntity);
        }
        return true;
    }

    @Override
    public boolean checkStayAccommodation(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        Optional<SearchRoomEntity> search = searchRoomRepository.findById(1);

        if (accommodation.isPresent() && search.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();
            SearchRoomEntity searchRoomEntity = search.get();

            LocalDate getCheckIn = searchRoomEntity.getCheckIn();
            LocalDate getCheckOut = searchRoomEntity.getCheckOut();

            long daysDiff = ChronoUnit.DAYS.between(getCheckIn, getCheckOut);

            if (accommodationEntity.getMinStay() <= daysDiff && accommodationEntity.getMaxStay() >= daysDiff) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkMonthAvailability(UUID uuidAccommodation) {

        Optional<AccommodationEntity> accommodation = accommodationRepository.findByUuid(uuidAccommodation);

        Optional<SearchRoomEntity> search = searchRoomRepository.findById(1);

        if (accommodation.isPresent() && search.isPresent()) {
            AccommodationEntity accommodationEntity = accommodation.get();
            SearchRoomEntity searchRoomEntity = search.get();

            int getMonthCheckIn = searchRoomEntity.getCheckIn().getMonthValue();
            int getMonthCheckOut = searchRoomEntity.getCheckOut().getMonthValue();

            List<Integer> availableMonths = accommodationEntity.getCalendar().getCalendarMonth();

            return availableMonths.contains(getMonthCheckIn) && availableMonths.contains(getMonthCheckOut);
        }
        return false;
    }
}