package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.Post;
import java.util.List;
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

    @Query(value = "select p.*, count(c.id) as comment_count from Post p left join post_comment c on p.id = c.post_id  where p.is_active = 1 "
        + " group by p.id order by comment_count desc limit :limit offset :offset", nativeQuery = true)
    List<Post> findAllPopular(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "select p.*, count(v.id) as vote_count from Post p left join post_vote v on p.id = v.post_id and v.value = 1  where p.is_active = 1 "
        + " group by p.id order by vote_count desc limit :limit offset :offset", nativeQuery = true)
    List<Post> findAllBest(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "from Post p where p.isActive = 1")
    Page<Post> findAllActive(Pageable pageable);

}
