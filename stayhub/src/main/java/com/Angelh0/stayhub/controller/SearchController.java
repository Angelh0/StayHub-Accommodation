package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.service.SearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/checks")
    public SearchRoomDTO getChecks() {
        return searchService.getChecks();
    }

    @GetMapping("/searchAdvanced/search/{city}/room/{room}/capacity/{capacity}/checkIn/{checkIn}/checkOut/{checkOut}")
    public ResponseEntity<List<ResponseAccommodationDTO>> searchAdvanced(
            @PathVariable String city,
            @PathVariable int room,
            @PathVariable int capacity,
            @PathVariable @DateTimeFormat(pattern = "d-M-yyyy") LocalDate checkIn,
            @PathVariable @DateTimeFormat(pattern = "d-M-yyyy") LocalDate checkOut) {

        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setCheckIn(checkIn);
        searchRoomDTO.setCheckOut(checkOut);

        List<ResponseAccommodationDTO> dtoList = searchService.searchAdvanced(searchRoomDTO, city, room, capacity, checkIn, checkOut);

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/searchAdvancedRoom/{uuid}")
    public ResponseEntity<List<ResponseRoomDTO>> searchAdvancedRoom(@PathVariable UUID uuid) {
        List<ResponseRoomDTO> room = searchService.searchAdvancedRoom(uuid);
        ResponseEntity<List<ResponseRoomDTO>> responseEntity = new ResponseEntity<>(room, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/lastSearch")
    public ResponseEntity<SearchRoomDTO> getLastSearch(Authentication authentication) {
        UUID uuidUser = UUID.fromString(authentication.getPrincipal().toString());
        SearchRoomDTO roomDTO = searchService.getLastSearch(uuidUser);
        ResponseEntity<SearchRoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }
}