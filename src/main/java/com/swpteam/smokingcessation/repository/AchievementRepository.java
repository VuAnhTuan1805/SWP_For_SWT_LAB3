package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Achievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, String> {

    Optional<Achievement> findByIdAndIsDeletedFalse(String id);

    Optional<Achievement> findByNameAndIsDeletedFalse(String name);

    Page<Achievement> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByNameAndIsDeletedFalse(String name);
}

