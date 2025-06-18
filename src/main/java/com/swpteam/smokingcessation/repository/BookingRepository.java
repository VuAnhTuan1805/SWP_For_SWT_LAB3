package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findByIdAndIsDeletedFalse(String id);

    Page<Booking> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByCoachIdAndIsDeletedFalseAndStartedAtLessThanAndEndedAtGreaterThan(
            String coachId, LocalDateTime endedAt, LocalDateTime startedAt
    );
}