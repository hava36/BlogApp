package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.PostView;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepositoryReadOnly extends ReadOnlyRepository<PostView, Integer> {

    @Query(value = "from PostView p where isActive = :isActive and moderationStatus = :status and time <= :time")
    Page<PostView> findByIsActiveAndStatusLessThenInstant(Pageable pageable,
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant);

    @Query(value = "select count(p) from Post p where isActive = :isActive and moderationStatus = :status and time <= :time")
    Long countByIsActiveAndStatusLessThenInstant(
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant);

    @Query(value = "from PostView p where isActive = :isActive and moderationStatus = :status and time between :startDate and :endDate")
    Page<PostView> findByIsActiveAndStatusBetweenDates(Pageable pageable,
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate);

    @Query(value = "select count(p) from Post p where isActive = :isActive and moderationStatus = :status and time between :startDate and :endDate")
    Long countByIsActiveAndStatusBetweenDates(
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate);

    @Query(value = "from PostView p where isActive = :isActive and moderationStatus = :status and time <= :time and (text like %:text% or title like %:text%)")
    List<PostView> findIsActiveAndStatusLessThenInstantAndTextContainingIgnoreCase(Pageable pageable,
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant,
        @Param("text") String text);

    @Query(value = "select count(p) from PostView p "
        + "where isActive = :isActive and moderationStatus = :status and time <= :time and (text like %:text% or title like %:text%)")
    Long countIsActivePostsAndStatusLessThenInstantAndTextContainingIgnoreCase(
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant,
        @Param("text") String text);

    @Query(value = "select * from post_view p where p.is_active = :isActive and p.moderation_status = :#{#status.name()} and time <= :time and p.id"
        + " in (select t2p.post_id from tag2post t2p where t2p.tag_id in (select t.id from tag t where t.name like %:tag%))", nativeQuery = true)
    List<PostView> findIsActiveAndStatusLessThenInstantAndByTag(Pageable pageable,
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant,
        @Param("tag") String tag);

    @Query(value = "select count(*) from post_view p where p.is_active = :isActive and p.moderation_status = :#{#status.name()} and time <= :time and p.id"
        + " in (select t2p.post_id from tag2post t2p where t2p.tag_id in (select t.id from tag t where t.name like %:tag%))", nativeQuery = true)
    Long countIsActiveAndStatusLessThenInstantAndByTag(
        @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status,
        @Param("time") Instant instant,
        @Param("tag") String tag);


    @Query(value = "select * from post_View p where p.is_active = :is_active and p.moderation_status in :statuses "
        + "and p.moderator_id = :user_id", nativeQuery = true)
    List<PostView> findIsActiveAndByStatusesAndByUserId(Pageable pageable,
        @Param("is_active") Integer isActive,
        @Param("statuses") Collection<String> statuses,
        @Param("user_id") Integer userId);

    @Query(value = "select count(*) from post_View p where p.is_active = :is_active and p.moderation_status in :statuses "
        + "and p.moderator_id = :user_id", nativeQuery = true)
    Long countIsActiveAndStatusLessThenInstantAndByUserId(
        @Param("is_active") Integer isActive,
        @Param("statuses") Collection<String> statuses,
        @Param("user_id") Integer userId);

}
