package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.service.AccommodationDraftService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1")
public class AccommodationDraftController {

    private final AccommodationDraftService accommodationDraftService;

    public AccommodationDraftController(AccommodationDraftService accommodationDraftService) {
        this.accommodationDraftService = accommodationDraftService;
    }

    @GetMapping("/basicInfo/{uuid}")
    public ResponseEntity<Boolean> createDraftAccommodation(@PathVariable UUID uuid) {
        boolean check = accommodationDraftService.checkBasicCreate(uuid);
        return ResponseEntity.ok(check);
    }

    @PatchMapping("/stayConfiguration/{uuid}")
    public ResponseEntity<AccommodationDTO> stayConfiguration(@RequestBody AccommodationDTO accommodationDTO, @PathVariable UUID uuid, Authentication authentication) {
        UUID uuidOwner = UUID.fromString(authentication.getPrincipal().toString());
        AccommodationDTO accommodation = accommodationDraftService.stayConfiguration(uuid, accommodationDTO, uuidOwner);
        ResponseEntity<AccommodationDTO> responseEntity = new ResponseEntity<>(accommodation, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/addRooms/{uuid}")
    public ResponseEntity<Boolean> addRooms(@PathVariable UUID uuid) {
        boolean check = accommodationDraftService.checkAddRooms(uuid);
        return ResponseEntity.ok(check);
    }

    @PatchMapping("/availabilityCalendar/{uuid}")
    public ResponseEntity<AccommodationDTO> availabilityCalendar(@RequestBody AccommodationDTO accommodationDTO, @PathVariable UUID uuid, Authentication authentication) {
        UUID uuidOwner = UUID.fromString(authentication.getPrincipal().toString());
        AccommodationDTO accommodation = accommodationDraftService.availabilityCalendar(uuid, accommodationDTO, uuidOwner);
        ResponseEntity<AccommodationDTO> responseEntity = new ResponseEntity<>(accommodation, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/checkAvailabilityCalendar/{uuid}")
    public ResponseEntity<Boolean> checkAvailabilityCalendar(@PathVariable UUID uuid) {
        boolean check = accommodationDraftService.checkAvailabilityCalendar(uuid);
        return ResponseEntity.ok(check);
    }

    @PatchMapping("/addPhotos/{uuid}")
    public ResponseEntity<AccommodationDTO> addPhotos(@PathVariable UUID uuid, @RequestBody AccommodationDTO accommodationDTO, Authentication authentication)  {
        UUID uuidOwner = UUID.fromString(authentication.getPrincipal().toString());
        AccommodationDTO accommodation = accommodationDraftService.addPhotos(uuid, accommodationDTO, uuidOwner);
        ResponseEntity<AccommodationDTO> responseEntity = new ResponseEntity<>(accommodation, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/checkAddPhotos/{uuid}")
    public ResponseEntity<Boolean> checkAddPhotos(@PathVariable UUID uuid) {
        boolean check = accommodationDraftService.checkAddPhotos(uuid);
        return ResponseEntity.ok(check);
    }

    @GetMapping("/publishAccommodation/{uuid}")
    public ResponseEntity<AccommodationDTO> publishAccommodation(@PathVariable UUID uuid) {
        AccommodationDTO accommodation = accommodationDraftService.publishAccommodation(uuid);
        ResponseEntity<AccommodationDTO> responseEntity = new ResponseEntity<>(accommodation, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/checkPublishAccommodation/{uuid}")
    public ResponseEntity<Boolean> checkPublishAccommodation(@PathVariable UUID uuid) {
        boolean check = accommodationDraftService.checkPublishAccommodation(uuid);
        return ResponseEntity.ok(check);
    }
}