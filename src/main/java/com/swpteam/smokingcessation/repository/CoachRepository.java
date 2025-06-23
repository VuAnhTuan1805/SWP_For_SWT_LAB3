package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {
    Page<Coach> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Coach> findByIdAndIsDeletedFalse(String id);
}