package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.PostCommentDto;
import com.skillbox.blogapp.model.entity.PostComment;
import com.skillbox.blogapp.repository.PostCommentRepository;
import com.skillbox.blogapp.service.PostCommentService;
import com.skillbox.blogapp.service.mapper.PostCommentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostComment}.
 */
@Service
@Transactional
public class PostCommentServiceImpl implements PostCommentService {

    private final Logger log = LoggerFactory.getLogger(PostCommentServiceImpl.class);

    private final PostCommentRepository postCommentRepository;

    private final PostCommentMapper postCommentMapper;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
        PostCommentMapper postCommentMapper) {
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
    }

    @Override
    public PostCommentDto save(PostCommentDto postCommentDto) {
        log.debug("Request to save PostComment : {}", postCommentDto);
        var postComment = postCommentMapper.toEntity(postCommentDto);
        postComment = postCommentRepository.save(postComment);
        return postCommentMapper.toDto(postComment);
    }

    @Override
    public Optional<PostCommentDto> partialUpdate(PostCommentDto postCommentDto) {
        log.debug("Request to partially update PostComment : {}", postCommentDto);

        return postCommentRepository
            .findById(postCommentDto.getId())
            .map(
                existingPostComment -> {
                    postCommentMapper.partialUpdate(existingPostComment, postCommentDto);
                    return existingPostComment;
                }
            )
            .map(postCommentRepository::save)
            .map(postCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostCommentDto> findAll() {
        log.debug("Request to get all PostComments");
        return postCommentRepository.findAll().stream().map(postCommentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostCommentDto> findOne(Integer id) {
        log.debug("Request to get PostComment : {}", id);
        return postCommentRepository.findById(id).map(postCommentMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete PostComment : {}", id);
        postCommentRepository.deleteById(id);
    }
}
