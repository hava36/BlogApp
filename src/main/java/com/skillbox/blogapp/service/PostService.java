package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.PostDto;
import com.skillbox.blogapp.model.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Post}.
 */
public interface PostService {

    /**
     * Save a post.
     *
     * @param postDto the entity to save.
     * @return the persisted entity.
     */
    PostDto save(PostDto postDto);

    /**
     * Partially updates a post.
     *
     * @param postDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostDto> partialUpdate(PostDto postDto);

    /**
     * Get all the posts.
     *
     * @return the list of entities.
     */
    List<PostDto> findAll();

    /**
     * Get all the posts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostDto> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDto> findOne(Integer id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
