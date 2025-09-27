package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.AccommodationDTO;
import com.Angelh0.stayhub.dto.RequestAccommodationDTO;
import com.Angelh0.stayhub.dto.ResponseAccommodationDTO;
import com.Angelh0.stayhub.service.AccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping("/create") //Modificado y comprobado
    public ResponseEntity<RequestAccommodationDTO> createAccommodation(@RequestBody RequestAccommodationDTO requestAccommodationDTO) {
        requestAccommodationDTO = accommodationService.createAccommodation(requestAccommodationDTO);
        ResponseEntity<RequestAccommodationDTO> responseEntity = new ResponseEntity<>(requestAccommodationDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/modified/{uuid}")
    public ResponseAccommodationDTO modifiedAccommodation(@RequestBody RequestAccommodationDTO requestAccommodationDTO, @PathVariable UUID uuid) {
        ResponseAccommodationDTO responseAccommodationDTO = accommodationService.modifiedAccommodation(requestAccommodationDTO, uuid);
        ResponseEntity<ResponseAccommodationDTO> responseEntity = new ResponseEntity<>(responseAccommodationDTO, HttpStatus.OK);
        return responseAccommodationDTO;
    }

    @GetMapping("/getAccommodation/{uuid}") //Modificado y comprobado
    public ResponseEntity getAccommodationById(@PathVariable UUID uuid) {
        AccommodationDTO accommodationDTO = accommodationService.getAccommodationById(uuid);
        ResponseEntity<AccommodationDTO> responseEntity = new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
        return responseEntity;
    }
}
