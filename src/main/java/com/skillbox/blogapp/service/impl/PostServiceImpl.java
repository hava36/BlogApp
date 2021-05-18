package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.PostDto;
import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.repository.PostRepository;
import com.skillbox.blogapp.repository.domain.OffsetBasedPageRequest;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.mapper.PostMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final Map<String, Map<String, PostSearchingHandler>> searchingHandlers;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.searchingHandlers = Map.of("mode", Map.of(
            "recent", new ModeRecentHandler(),
            "early", new ModeEarlyHandler(),
            "popular", new ModePopularHandler(),
            "best", new ModeBestHandler()));
    }

    @Override
    public PostDto save(PostDto postDto) {
        log.debug("Request to save Post : {}", postDto);
        var post = postMapper.toEntity(postDto);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public Optional<PostDto> partialUpdate(PostDto postDto) {
        log.debug("Request to partially update Post : {}", postDto);

        return postRepository
            .findById(postDto.getId())
            .map(
                existingPost -> {
                    postMapper.partialUpdate(existingPost, postDto);
                    return existingPost;
                }
            )
            .map(postRepository::save)
            .map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        log.debug("Request to get all Posts");
        return postRepository
            .findAll()
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all posts by filter.
     *
     * @return the list of entities.
     */
    @Override
    public List<PostDto> findAllWithOrderByModeLimitOffset(Integer offset, Integer limit,
        String mode) {

        if (!searchingHandlers.containsKey("mode")
            && !searchingHandlers.get("mode").containsKey(mode)) {
            throw new IllegalArgumentException(
                String.format("Unrecognized mode parameter %s", mode));
        }

        return searchingHandlers
            .get("mode").get(mode)
            .findAll(offset, limit)
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDto> findOne(Integer id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    private interface PostSearchingHandler {

        List<Post> findAll(int offset, int limit, String... params);

    }

    private class ModeRecentHandler implements PostSearchingHandler {

        @Override
        public List<Post> findAll(int offset, int limit, String... params) {
            return postRepository
                .findAll(new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"))).getContent();
        }
    }

    private class ModeEarlyHandler implements PostSearchingHandler {

        @Override
        public List<Post> findAll(int offset, int limit, String... params) {
            return postRepository
                .findAll(new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.ASC, "time"))).getContent();
        }
    }

    private class ModePopularHandler implements PostSearchingHandler {

        @Override
        public List<Post> findAll(int offset, int limit, String... params) {
            return postRepository.findAllPopularPosts(offset, limit);
        }
    }

    private class ModeBestHandler implements PostSearchingHandler {

        @Override
        public List<Post> findAll(int offset, int limit, String... params) {
            return postRepository.findAllBestPosts(offset, limit);
        }

    }

}
