package com.Angelh0.stayhub.repository;

import com.Angelh0.stayhub.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> uuid(UUID uuid);
    Optional<RoomEntity> findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("delete from RoomEntity r where r.uuid = :uuid")
    void deleteByUuid(UUID uuid);

    List<RoomEntity> findByAccommodation_CityAndRoomAndCapacity(String city, int rooms, int capacity);

}
