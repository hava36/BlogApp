package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Post entity.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByIsActive(Pageable pageable, @Param("is_active") Integer isActive);

}
