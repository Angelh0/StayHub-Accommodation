package com.Angelh0.stayhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "LAST_SEARCH")
@Getter
@Setter
public class LastSearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private UUID uuidUser;

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