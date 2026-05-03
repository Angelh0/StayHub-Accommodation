package com.Angelh0.stayhub.repository;

import com.Angelh0.stayhub.entity.LastSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SearchRoomRepository extends JpaRepository<LastSearchEntity, Integer> {
    Optional<LastSearchEntity> findByUuidUser(UUID uuidUser);
}
