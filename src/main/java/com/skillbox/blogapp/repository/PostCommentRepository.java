package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PostComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

}
