package com.Angelh0.stayhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="SEARCH_RESULT_TABLE")
@Getter
@Setter
public class SearchResult {

    @Id
    private int id = 1;

    @Column(nullable = false)
    private UUID uuidSearch;

    @OneToMany
    List<AccommodationEntity> accommodation;

    @OneToMany
    List<RoomEntity> rooms;

    @PrePersist
    public void generatedUuid() {
        if (uuidSearch == null) {
            uuidSearch = UUID.randomUUID();
        }
    }
}
