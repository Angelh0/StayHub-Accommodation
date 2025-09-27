package com.Angelh0.stayhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "ROOM_SEARCH")
@Getter
@Setter
public class SearchRoomEntity {

    @Id
    private int id = 1;

    @Column(nullable = false)
    private UUID searchUuid;

    private String city;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int room;

    private int capacity;

    @PrePersist
    public void generatedUuid() {
        if (searchUuid == null) {
            searchUuid = UUID.randomUUID();
        }
    }

}


