package com.skillbox.blogapp.repository;

import com.skillbox.blogapp.model.entity.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PostVote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Integer> {

}
