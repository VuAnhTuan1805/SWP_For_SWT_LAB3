package com.swpteam.smokingcessation.repository;

import com.swpteam.smokingcessation.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String>  {

    Page<Comment> findByAccountId(String accountId, Pageable pageable);

    Page<Comment> findByBlogIdAndLevel(String blogId, int level, Pageable pageable);

    Page<Comment> findAll(Pageable pageable);
}
