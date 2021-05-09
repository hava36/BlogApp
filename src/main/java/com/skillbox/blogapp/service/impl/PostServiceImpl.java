package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.PostDto;
import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.repository.PostRepository;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.mapper.PostMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
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
            .findAllWithEagerRelationships()
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<PostDto> findAllWithEagerRelationships(Pageable pageable) {
        return postRepository.findAllWithEagerRelationships(pageable).map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDto> findOne(Integer id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findOneWithEagerRelationships(id).map(postMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }
}
