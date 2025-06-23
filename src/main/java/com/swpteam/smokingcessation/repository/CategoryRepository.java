package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByName(String name);

    Page<Category> findByCreatedBy(String createdBy, Pageable pageable);

    Page<Category> findAll(Pageable pageable);
}
