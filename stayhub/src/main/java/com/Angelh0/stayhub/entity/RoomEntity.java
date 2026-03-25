package com.Angelh0.stayhub.entity;

import com.Angelh0.stayhub.enums.RoomEnums.RoomStatus;
import com.Angelh0.stayhub.enums.RoomEnums.RoomType;
import com.Angelh0.stayhub.enums.RoomEnums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
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
    private UUID uuidOwner;

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
    @Column(name = "RoomStatus")
    private RoomStatus roomStatus = RoomStatus.Active;

    @Enumerated(EnumType.STRING)
    @Column(name="Statustype")
    private StatusType status;

    private double totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ElementCollection
    @Column(name = "RoomPhotos")
    private List<String> photos;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccommodationEntity accommodation;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}

