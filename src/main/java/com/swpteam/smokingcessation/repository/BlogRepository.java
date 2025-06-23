package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

    Optional<Blog> findByIdAndIsDeletedFalse(String id);

    Optional<Blog> findBySlugAndIsDeletedFalse(String slug);

    Page<Blog> findAllByAccountIdAndIsDeletedFalse(String accountId, Pageable pageable);

    Page<Blog> findByCategoryIdAndIsDeletedFalse(String categoryId, Pageable pageable);

    List<Blog> findByCategoryId(String categoryId);

    Page<Blog> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsBySlug(String slug);
}
