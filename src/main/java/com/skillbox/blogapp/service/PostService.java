package com.skillbox.blogapp.service;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.model.response.CalendarResponseList;
import com.skillbox.blogapp.model.response.PostResponseList;
import com.skillbox.blogapp.service.dto.post.PostDetailedItem;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Interface for managing {@link Post}.
 */
public interface PostService {

    PostDetailedItem save(PostDetailedItem postBriefDto);

    Optional<PostDetailedItem> partialUpdate(PostDetailedItem postBriefDto);

    PostResponseList findEnabledByMode(String mode, Long offset, Integer limit);

    PostResponseList findEnabledByContent(String content, Long offset, Integer limit);

    PostResponseList findEnabledByDate(LocalDate date, Long offset, Integer limit);

    PostResponseList findEnabledByTag(String tag, Long offset, Integer limit);

    PostResponseList findUserPostsByStatus(String sessionId, String status, Long offset, Integer limit);

    CalendarResponseList findEnabledWithinYear(Integer year);

    Optional<PostDetailedItem> findOne(Integer id);

    void delete(Integer id);

}
