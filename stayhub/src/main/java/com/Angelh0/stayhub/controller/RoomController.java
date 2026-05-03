package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.accommodation.AccommodationDTO;
import com.Angelh0.stayhub.dto.accommodation.ResponseAccommodationDTO;
import com.Angelh0.stayhub.dto.room.*;
import com.Angelh0.stayhub.dto.search.SearchRoomDTO;
import com.Angelh0.stayhub.entity.RoomEntity;
import com.Angelh0.stayhub.service.RoomService;
import com.Angelh0.stayhub.service.SearchService;
import jakarta.validation.Valid;
import org.hibernate.validator.cfg.defs.UUIDDef;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO, @PathVariable UUID uuid, Authentication authentication) {
        UUID userUUID = UUID.fromString(authentication.getPrincipal().toString());
        roomDTO = roomService.createRoom(roomDTO, uuid, userUUID);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/modifiedRoom/{uuid}")
    public ResponseEntity<RoomDTO> modifiedRoom(@RequestBody UpdateRoomDTO updateRoomDTO, @PathVariable UUID uuid, Authentication authentication) {
        UUID userUUID = UUID.fromString(authentication.getPrincipal().toString());
        RoomDTO roomDTO = roomService.modifiedRooms(updateRoomDTO, uuid, userUUID);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/getRooms/{uuid}")
    public ResponseEntity<ResponseRoomDTO> getRooms(@PathVariable UUID uuid) {
        ResponseRoomDTO responseRoomDTO = roomService.getRooms(uuid);
        ResponseEntity<ResponseRoomDTO> responseEntity = new ResponseEntity<>(responseRoomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/get-Rooms/{uuid}")
    public ResponseEntity<RoomDTO> getRoomsWithUuid(@PathVariable UUID uuid) {
        RoomDTO roomDTO = roomService.getRoomsWithUuid(uuid);
        ResponseEntity<RoomDTO> responseEntity = new ResponseEntity<>(roomDTO, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("deleteRoom/{uuid}")
    public ResponseEntity<String> deleteRoomByUuid(@PathVariable UUID uuid) {
        roomService.deleteByUuid(uuid);
        return ResponseEntity.ok("Room with UUID: [" + uuid + "] deleted successfully");
    }

    @GetMapping("/getMyRooms")
    public ResponseEntity<List<RoomAndAccDTO>> getMyRooms(Authentication authentication) {
        UUID userUUID = UUID.fromString(authentication.getPrincipal().toString());
        List<RoomAndAccDTO> roomDTO = roomService.getMyRooms(userUUID);
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
    }

    @PostMapping("/room-block/{uuid}")
    public ResponseEntity<String> blockRoom(@PathVariable UUID uuid, @RequestBody CreateBlockDTO blockDTO, Authentication authentication) {
        UUID userUUID = UUID.fromString(authentication.getPrincipal().toString());
        String result = roomService.blockRoom(uuid, userUUID, blockDTO);

        if (result.startsWith("ERROR:")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

