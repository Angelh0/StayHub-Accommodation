package com.Angelh0.stayhub.repository;

import com.Angelh0.stayhub.entity.SearchResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchResultRepository extends JpaRepository<SearchResultEntity, Integer> {
    Optional<SearchResultEntity> findById(Integer integer);
}
