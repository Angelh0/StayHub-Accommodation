package com.Angelh0.stayhub.controller;

import com.Angelh0.stayhub.dto.RoomAdminDTO;
import com.Angelh0.stayhub.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminRoomController {

    private final RoomService roomService;

    public AdminRoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/admin/getAllRooms")
    public ResponseEntity<List<RoomAdminDTO>> getAllRooms() {
        List<RoomAdminDTO> dto = roomService.getAllRooms();
        ResponseEntity<List<RoomAdminDTO>> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
        return responseEntity;
    }
}
