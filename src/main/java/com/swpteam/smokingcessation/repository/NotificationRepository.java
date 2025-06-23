package com.swpteam.smokingcessation.repository;


import com.swpteam.smokingcessation.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    Optional<Notification> findByIdAndIsDeletedFalse(String id);

    List<Notification> findAllByAccountIdAndIsDeletedFalse(String id);

    Page<Notification> findAllByIsDeletedFalse(Pageable pageable);

    Page<Notification> findByAccountIdAndIsDeletedFalse(String id, Pageable pageable);
}
