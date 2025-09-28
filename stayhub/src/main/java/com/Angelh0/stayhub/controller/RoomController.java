package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.RoomDTO;
import com.Angelh0.stayhub.dto.SearchRoomDTO;
import com.Angelh0.stayhub.service.RoomService;
import com.Angelh0.stayhub.service.SearchService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RoomController {

    private final SearchService searchService;
    private RoomService roomService;

    public RoomController(RoomService roomService, SearchService searchService) {
        this.roomService = roomService;
        this.searchService = searchService;
    }

    @PostMapping("/createRoom/{uuid}")
    public ResponseEntity<RoomDTO> createRoom( @RequestBody RoomDTO roomDTO, @PathVariable UUID uuid) {
        roomDTO = roomService.createRoom(roomDTO, uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/modifiedRoom/{uuid}")
    public ResponseEntity<RoomDTO> modifiedRoom(@RequestBody RoomDTO roomDTO, @PathVariable UUID uuid) {
        roomDTO = roomService.modifiedRooms(roomDTO, uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/getRooms/{uuid}")
    public ResponseEntity<RoomDTO> getRooms(@PathVariable UUID uuid) {
        RoomDTO roomDTO = roomService.getRooms(uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("deleteRoom/{uuid}")
    public ResponseEntity<String> deleteRoomByUuid(@PathVariable UUID uuid) {

        roomService.deleteByUuid(uuid);
        return ResponseEntity.ok("Room with UUID: [" + uuid + "] deleted successfully");
    }

    @GetMapping("/getAdvanceRoom/search/{city}/room/{room}/capacity/{capacity}/checkIn/{checkIn}/checkOut/{checkOut}")
    public ResponseEntity<List<RoomDTO>> searchAdvanced(
            @PathVariable String city,
            @PathVariable int room,
            @PathVariable int capacity,
            @PathVariable @DateTimeFormat(pattern = "d-M-yyyy") LocalDate checkIn,
            @PathVariable @DateTimeFormat(pattern = "d-M-yyyy") LocalDate checkOut) {

        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        searchRoomDTO.setCheckIn(checkIn);
        searchRoomDTO.setCheckOut(checkOut);

        List<RoomDTO> dtoList = searchService.searchAdvanced(searchRoomDTO, city, room, capacity, checkIn, checkOut);

        return ResponseEntity.ok(dtoList);
    }

}

