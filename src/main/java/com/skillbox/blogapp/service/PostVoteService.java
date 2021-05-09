package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.PostVoteDto;
import com.skillbox.blogapp.model.entity.PostVote;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PostVote}.
 */
public interface PostVoteService {

    /**
     * Save a postVote.
     *
     * @param postVoteDto the entity to save.
     * @return the persisted entity.
     */
    PostVoteDto save(PostVoteDto postVoteDto);

    /**
     * Partially updates a postVote.
     *
     * @param postVoteDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostVoteDto> partialUpdate(PostVoteDto postVoteDto);

    /**
     * Get all the postVotes.
     *
     * @return the list of entities.
     */
    List<PostVoteDto> findAll();

    /**
     * Get the "id" postVote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostVoteDto> findOne(Integer id);

    /**
     * Delete the "id" postVote.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
