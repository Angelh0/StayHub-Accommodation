package com.Angelh0.stayhub.repository;

import com.Angelh0.stayhub.entity.AccommodationDraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccommodationDraftRepository extends JpaRepository<AccommodationDraftEntity, Long> {
    Optional<AccommodationDraftEntity> findByAccommodationUuid(UUID uuid);
}
