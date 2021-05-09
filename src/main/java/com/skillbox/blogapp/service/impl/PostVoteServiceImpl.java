package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.PostVoteDto;
import com.skillbox.blogapp.model.entity.PostVote;
import com.skillbox.blogapp.repository.PostVoteRepository;
import com.skillbox.blogapp.service.PostVoteService;
import com.skillbox.blogapp.service.mapper.PostVoteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostVote}.
 */
@Service
@Transactional
public class PostVoteServiceImpl implements PostVoteService {

    private final Logger log = LoggerFactory.getLogger(PostVoteServiceImpl.class);

    private final PostVoteRepository postVoteRepository;

    private final PostVoteMapper postVoteMapper;

    public PostVoteServiceImpl(PostVoteRepository postVoteRepository,
        PostVoteMapper postVoteMapper) {
        this.postVoteRepository = postVoteRepository;
        this.postVoteMapper = postVoteMapper;
    }

    @Override
    public PostVoteDto save(PostVoteDto postVoteDto) {
        log.debug("Request to save PostVote : {}", postVoteDto);
        var postVote = postVoteMapper.toEntity(postVoteDto);
        postVote = postVoteRepository.save(postVote);
        return postVoteMapper.toDto(postVote);
    }

    @Override
    public Optional<PostVoteDto> partialUpdate(PostVoteDto postVoteDto) {
        log.debug("Request to partially update PostVote : {}", postVoteDto);

        return postVoteRepository
            .findById(postVoteDto.getId())
            .map(
                existingPostVote -> {
                    postVoteMapper.partialUpdate(existingPostVote, postVoteDto);
                    return existingPostVote;
                }
            )
            .map(postVoteRepository::save)
            .map(postVoteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostVoteDto> findAll() {
        log.debug("Request to get all PostVotes");
        return postVoteRepository.findAll().stream().map(postVoteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostVoteDto> findOne(Integer id) {
        log.debug("Request to get PostVote : {}", id);
        return postVoteRepository.findById(id).map(postVoteMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete PostVote : {}", id);
        postVoteRepository.deleteById(id);
    }
}
