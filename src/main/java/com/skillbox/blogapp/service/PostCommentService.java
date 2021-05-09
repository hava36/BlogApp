package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.PostCommentDto;
import com.skillbox.blogapp.model.entity.PostComment;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PostComment}.
 */
public interface PostCommentService {

    /**
     * Save a postComment.
     *
     * @param postCommentDto the entity to save.
     * @return the persisted entity.
     */
    PostCommentDto save(PostCommentDto postCommentDto);

    /**
     * Partially updates a postComment.
     *
     * @param postCommentDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostCommentDto> partialUpdate(PostCommentDto postCommentDto);

    /**
     * Get all the postComments.
     *
     * @return the list of entities.
     */
    List<PostCommentDto> findAll();

    /**
     * Get the "id" postComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostCommentDto> findOne(Integer id);

    /**
     * Delete the "id" postComment.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
