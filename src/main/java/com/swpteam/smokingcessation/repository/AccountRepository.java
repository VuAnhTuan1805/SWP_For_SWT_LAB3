package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByIdAndIsDeletedFalse(String id);

    Optional<Account> findByUsernameAndIsDeletedFalse(String username);

    Optional<Account> findByEmailAndIsDeletedFalse(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<Account> findAllByIsDeletedFalse(Pageable pageable);

    Page<Account> findAll(Pageable pageable);

    // Get admin emails
    @Query("SELECT a.email FROM Account a WHERE a.role = 'ADMIN' AND a.isDeleted = false")
    List<String> findAllAdminEmails();
}
