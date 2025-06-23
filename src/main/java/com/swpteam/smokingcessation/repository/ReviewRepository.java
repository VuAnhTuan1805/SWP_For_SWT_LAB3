package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByIdAndIsDeletedFalse(String id);

    Page<Review> findByMemberIdAndIsDeletedFalse(String memberId, Pageable pageable);

    Page<Review> findByCoachIdAndIsDeletedFalse(String coachId, Pageable pageable);

    Page<Review> findAllByIsDeletedFalse(Pageable pageable);
}
