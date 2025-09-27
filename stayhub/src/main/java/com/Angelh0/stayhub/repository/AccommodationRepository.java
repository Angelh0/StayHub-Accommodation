package com.Angelh0.stayhub.repository;

import com.Angelh0.stayhub.entity.AccommodationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {
    List<AccommodationEntity> uuid(UUID uuid);
    List<AccommodationEntity> findByCity(String city);
    Optional<AccommodationEntity> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
