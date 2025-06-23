package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.AITokenUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AITokenUsageRepository extends JpaRepository<AITokenUsage, String> {

    Optional<AITokenUsage> findByAccountIdAndDate(String accountId, LocalDate date);
}
