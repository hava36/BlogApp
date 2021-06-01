package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "select date(time) as day_of_year, count(id) as post_count from post "
        + "where year(time) = :year and is_active = :isActive and moderation_status = :#{#status.name()} and time <= :time "
        + "group by day_of_year order by day_of_year;", nativeQuery = true)
    List<Object[]> findIsActiveAndYearAndStatusLessThenInstantGroupingByDays(@Param("year") Integer year, @Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status, @Param("time") Instant time);

    @Query(value = "select year(time) as post_year from post "
        + "where is_active = :isActive and moderation_status = :#{#status.name()} and time <= :time "
        + "group by post_year order by post_year;", nativeQuery = true)
    Set<Integer> findIsActiveAndYearAndStatusLessThenInstantGroupingByYear(@Param("isActive") Integer isActive,
        @Param("status") ModerationStatus status, @Param("time") Instant time);


}
