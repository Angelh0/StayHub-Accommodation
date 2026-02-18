package com.Angelh0.stayhub.entity;

import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationStatus;
import com.Angelh0.stayhub.enums.AccommodationEnums.AccommodationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ACCOMMODATION_TABLE")
@Getter
@Setter
public class AccommodationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    private UUID uuidOwner;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public AccommodationType type;

    @Column
    private String description;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private int availability;

    @Column
    private Double priceMax;

    @Column
    private Double priceMin;

    @Column
    private int minStay;

    @Column
    private int maxStay;

    @ElementCollection
    @Column(name = "photo_url")
    private List<String> photos;

    @Enumerated(EnumType.STRING)
    @Column(name = "AccommodationStatus")
    private AccommodationStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id", referencedColumnName = "id")
    private AccommodationCalendarEntity calendar;

    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private AccommodationDraftEntity draft;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RoomEntity> rooms;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        status = AccommodationStatus.Draft;

        if (calendar == null) {
            AccommodationCalendarEntity accommodationCalendarEntity = new AccommodationCalendarEntity();
            accommodationCalendarEntity.setAccommodation(this);
            this.calendar = accommodationCalendarEntity;
        }
    }
}
