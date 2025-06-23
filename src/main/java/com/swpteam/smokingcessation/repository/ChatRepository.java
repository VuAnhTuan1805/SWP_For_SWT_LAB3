package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
    boolean existsByAccountIdAndIsDeletedFalse(String accountId);

    Optional<Chat> findByIdAndIsDeletedFalse(String id);

    Page<Chat> findAllByIsDeletedFalse(Pageable pageable);

    Page<Chat> findByAccountIdAndIsDeletedFalse(String id, Pageable pageable);
}
