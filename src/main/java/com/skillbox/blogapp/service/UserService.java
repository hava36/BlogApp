package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.dto.UserDto;
import com.skillbox.blogapp.model.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link User}.
 */
public interface UserService {

    /**
     * Save a users.
     *
     * @param userDto the entity to save.
     * @return the persisted entity.
     */
    UserDto save(UserDto userDto);

    /**
     * Partially updates a users.
     *
     * @param userDto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDto> partialUpdate(UserDto userDto);

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<UserDto> findAll();

    /**
     * Get the "id" users.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDto> findOne(Integer id);

    /**
     * Delete the "id" users.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
