package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.service.AccommodationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AdminAccommodationController {

    private final AccommodationService accommodationService;

    public AdminAccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("admin/getAccommodation") //Modificado y comprobado
    public ResponseEntity<List<ResponseAccommodationDTO>> getAllAccommodation() {
        List<ResponseAccommodationDTO> accommodationList = accommodationService.getAllAccommodations();
        ResponseEntity<List<ResponseAccommodationDTO>> responseEntity = new ResponseEntity<>(accommodationList, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("admin/deleteAccommodation/{uuid}")
    public ResponseEntity<String> deleteAccommodation(@PathVariable UUID uuid) {

        accommodationService.deleteAccommodation(uuid);
        return ResponseEntity.ok("Alojamiento con UUID: [" + uuid + "] eliminado correctamente");
    }

    @GetMapping("admin/getAccommodation/search/{city}")
    public ResponseEntity <List<ResponseAccommodationDTO>> getAccommodationByCity(@PathVariable String city) {
        List<ResponseAccommodationDTO> accList = accommodationService.getAccommodationByCity(city);
        ResponseEntity<List<ResponseAccommodationDTO>> responseEntity = new ResponseEntity<>(accList, HttpStatus.OK);
        return responseEntity;
    }
}
