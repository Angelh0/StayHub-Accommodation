package com.Angelh0.stayhub.entity;

import com.Angelh0.stayhub.enums.RoomType;
import com.Angelh0.stayhub.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ROOM_TABLE")
@Getter
@Setter

public class RoomEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idRoom;

    @Column(nullable = false)
    private UUID uuid;

    @Column
    private int room;

    @Column
    private int capacity;

    @Column
    private int beds;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private RoomType type;

    @Column
    private double price;

    @Column
    private double areaInSquareMeters;

    @Enumerated(EnumType.STRING)
    @Column(name="Statustype")
    private StatusType status;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccommodationEntity accommodation;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}

