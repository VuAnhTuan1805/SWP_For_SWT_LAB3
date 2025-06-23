package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Phase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhaseRepository extends JpaRepository<Phase, String> {
    Page<Phase> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Phase> findByIdAndIsDeletedFalse(String id);
}