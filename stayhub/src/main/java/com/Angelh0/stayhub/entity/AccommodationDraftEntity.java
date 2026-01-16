package com.Angelh0.stayhub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ACCOMMODATION_DRAFT_TABLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;

    private UUID uuidDraft;
    private boolean basicCreate;
    private boolean StayCustomer;
    private boolean addRooms;
    private boolean availabilityCalendar;
    private boolean addPhotos;
    private boolean publish;

    @PrePersist
    public void generatedValue() {
        if (uuidDraft == null) {
            uuidDraft = UUID.randomUUID();
        }
    }
}
