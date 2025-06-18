package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.TimeTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

    public interface TimeTableRepository extends JpaRepository<TimeTable, String> {
        Page<TimeTable> findAllByIsDeletedFalse(Pageable pageable);

        Optional<TimeTable> findByIdAndIsDeletedFalse(String id);

        Page<TimeTable> findByCoachIdAndIsDeletedFalse(String coachId, Pageable pageable);

        Optional<TimeTable> findByCoachIdAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                String coachId, LocalDateTime bookingStart, LocalDateTime bookingEnd
        );
    }

