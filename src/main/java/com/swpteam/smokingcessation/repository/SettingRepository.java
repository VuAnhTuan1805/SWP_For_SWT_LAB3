package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.enums.MotivationFrequency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

    List<Setting> findByReportDeadlineAndIsDeletedFalse(LocalTime deadlineTime);

    @Query("SELECT s FROM Setting s " +
            "WHERE s.motivationFrequency = :frequency " +
            "AND s.isDeleted = false " +
            "AND s.account.role NOT IN ('ADMIN', 'COACH')")
    List<Setting> findByMotivationFrequencyAndIsDeletedFalse(@Param("frequency") MotivationFrequency frequency);

    List<Setting> findAllByIsDeletedFalse();

    Optional<Setting> findByIdAndIsDeletedFalse(String id);

    Optional<Setting> findByAccountIdAndIsDeletedFalse(String accountId);

    Page<Setting> findAllByIsDeletedFalse(Pageable pageable);
}
