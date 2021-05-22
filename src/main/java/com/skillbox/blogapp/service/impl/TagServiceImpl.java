package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.dto.TagDto;
import com.skillbox.blogapp.model.entity.Tag;
import com.skillbox.blogapp.repository.TagRepository;
import com.skillbox.blogapp.repository.TagViewReadOnlyRepository;
import com.skillbox.blogapp.service.TagService;
import com.skillbox.blogapp.service.mapper.TagMapper;
import com.skillbox.blogapp.service.mapper.TagViewMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tag}.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    private final TagViewReadOnlyRepository tagViewReadOnlyRepository;

    private final TagMapper tagMapper;

    private final TagViewMapper tagViewMapper;

    public TagServiceImpl(TagRepository tagRepository, TagViewReadOnlyRepository tagViewReadOnlyRepository, TagMapper tagMapper,
        TagViewMapper tagViewMapper) {
        this.tagRepository = tagRepository;
        this.tagViewReadOnlyRepository = tagViewReadOnlyRepository;
        this.tagMapper = tagMapper;
        this.tagViewMapper = tagViewMapper;
    }

    @Override
    public TagDto save(TagDto tagDto) {
        log.debug("Request to save Tag : {}", tagDto);
        var tag = tagMapper.toEntity(tagDto);
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public Optional<TagDto> partialUpdate(TagDto tagDto) {
        log.debug("Request to partially update Tag : {}", tagDto);

        return tagRepository
            .findById(tagDto.getId())
            .map(
                existingTag -> {
                    tagMapper.partialUpdate(existingTag, tagDto);
                    return existingTag;
                }
            )
            .map(tagRepository::save)
            .map(tagMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> findAll() {
        log.debug("Request to get all Tags");
        return tagRepository.findAll().stream().map(tagMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> findViewByNameContainingIgnoreCase(String filterByName) {
        return tagViewReadOnlyRepository.findByNameContainingIgnoreCase(filterByName).stream().map(tagViewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TagDto> findOne(Integer id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id).map(tagMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }

}
