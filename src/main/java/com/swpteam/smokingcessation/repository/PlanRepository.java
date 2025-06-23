package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan,String> {
    Page<Plan> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Plan> findByIdAndIsDeletedFalse(String id);
}
