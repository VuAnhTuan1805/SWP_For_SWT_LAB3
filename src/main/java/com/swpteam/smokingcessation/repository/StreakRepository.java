package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreakRepository extends JpaRepository<Streak, String> {
    Optional<Streak> findByMember_Account_Id(String accountId);
}
