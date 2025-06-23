package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.RefreshToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    boolean existsById(@NotNull String id);

    void deleteAllByExpiryTimeBefore(Date now);
}
