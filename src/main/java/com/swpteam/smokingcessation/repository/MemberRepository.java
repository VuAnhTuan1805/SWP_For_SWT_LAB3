package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByIdAndIsDeletedFalse(String s);

    Optional<Member> findByAccountIdAndIsDeletedFalse(String s);

    boolean existsByFullName(String fullName);

    Page<Member> findAllByIsDeletedFalse(Pageable pageable);
}
