package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "from Post p where isActive = :isActive and moderationStatus = :status and time <= :time")
    Page<Post> findByIsActiveAndStatusLessThenInstant(Pageable pageable, @Param("isActive") Integer isActive, @Param("status") ModerationStatus status,
        @Param("time") Instant instant);

}
