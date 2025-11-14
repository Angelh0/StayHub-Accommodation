package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.ResponseRoomDTO;
import com.Angelh0.stayhub.dto.room.RoomDTO;
import com.Angelh0.stayhub.dto.room.UpdateRoomDTO;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.service.RoomService;
import com.Angelh0.stayhub.service.SearchService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO, @PathVariable UUID uuid) {
        roomDTO = roomService.createRoom(roomDTO, uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/modifiedRoom/{uuid}")
    public ResponseEntity<RoomDTO> modifiedRoom(@RequestBody UpdateRoomDTO updateRoomDTO, @PathVariable UUID uuid) {
        RoomDTO roomDTO = roomService.modifiedRooms(updateRoomDTO, uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/getRooms/{uuid}")
    public ResponseEntity<ResponseRoomDTO> getRooms(@PathVariable UUID uuid) {
        ResponseRoomDTO responseRoomDTO = roomService.getRooms(uuid);
        ResponseEntity<ResponseRoomDTO> responseEntity = new ResponseEntity<>(responseRoomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("deleteRoom/{uuid}")
    public ResponseEntity<String> deleteRoomByUuid(@PathVariable UUID uuid) {

        roomService.deleteByUuid(uuid);
        return ResponseEntity.ok("Room with UUID: [" + uuid + "] deleted successfully");
    }



}

