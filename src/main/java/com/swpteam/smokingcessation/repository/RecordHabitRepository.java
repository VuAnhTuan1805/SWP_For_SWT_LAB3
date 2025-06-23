package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.RecordHabit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RecordHabitRepository extends JpaRepository<RecordHabit, String> {

    Optional<RecordHabit> findByIdAndIsDeletedFalse(String id);

    Optional<RecordHabit> findByAccountIdAndDateAndIsDeletedFalse(String accountId, LocalDate date);

    Page<RecordHabit> findByAccountIdAndIsDeletedFalse(String accountId, Pageable pageable);

    Page<RecordHabit> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByAccountIdAndDateAndIsDeletedFalse(String id, LocalDate time);
}