package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.PostView;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepositoryReadOnly extends ReadOnlyRepository<PostView, Integer> {

    @Query(value = "from PostView p where isActive = :isActive and moderationStatus = :status and time <= :instant")
    Page<PostView> findByIsActiveAndStatusLessThenInstant(Pageable pageable, @Param("isActive") Integer isActive, @Param("status") ModerationStatus status,
        @Param("instant") Instant instant);

    @Query(value = "select count(p) from Post p where isActive = :isActive and moderationStatus = :status and time <= :time")
    Long countByIsActiveAndStatusLessThenInstant(@Param("isActive") Integer isActive, @Param("status") ModerationStatus status,
        @Param("time") Instant instant);

}
