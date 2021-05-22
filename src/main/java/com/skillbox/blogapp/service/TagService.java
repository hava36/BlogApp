package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.TagDto;
import com.skillbox.blogapp.model.entity.Tag;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Tag}.
 */
public interface TagService {

    /**
     * Save a tag.
     *
     * @param tagDto the entity to save.
     * @return the persisted entity.
     */
    TagDto save(TagDto tagDto);

    /**
     * Partially updates a tag.
     *
     * @param tagDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TagDto> partialUpdate(TagDto tagDto);

    /**
     * Get all the tags.
     *
     * @return the list of entities.
     */
    List<TagDto> findAll();


    List<TagDto> findViewByNameContainingIgnoreCase(String filterByName);

    /**
     * Get the "id" tag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagDto> findOne(Integer id);

    /**
     * Delete the "id" tag.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);

}
