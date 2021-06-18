package com.skillbox.blogapp.service.impl;

import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import com.skillbox.blogapp.model.response.CalendarResponseList;
import com.skillbox.blogapp.model.response.PostResponseList;
import com.skillbox.blogapp.repository.PostRepository;
import com.skillbox.blogapp.repository.PostViewRepositoryReadOnly;
import com.skillbox.blogapp.repository.domain.OffsetBasedPageRequest;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.dto.post.PostBriefDto;
import com.skillbox.blogapp.service.dto.post.PostDetailedDto;
import com.skillbox.blogapp.service.mapper.PostDetailedMapper;
import com.skillbox.blogapp.service.mapper.PostViewBriefMapper;
import java.math.BigInteger;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    public static final Map<String, Sort> MODE_SORTING_SETTINGS = Map.of("best", Sort.by(Direction.DESC, "likeCount"),
        "popular", Sort.by(Direction.DESC, "commentCount"),
        "recent", Sort.by(Direction.DESC, "time"),
        "early", Sort.by(Direction.ASC, "time"));
    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;

    private final PostDetailedMapper postDetailedMapper;

    private final PostViewRepositoryReadOnly postViewRepository;

    private final PostViewBriefMapper postViewBriefMapper;

    public PostServiceImpl(PostRepository postRepository, PostDetailedMapper postDetailedMapper,
        PostViewRepositoryReadOnly postViewRepository,
        PostViewBriefMapper postViewBriefMapper) {
        this.postRepository = postRepository;
        this.postDetailedMapper = postDetailedMapper;
        this.postViewRepository = postViewRepository;
        this.postViewBriefMapper = postViewBriefMapper;
    }

    @Override
    public PostDetailedDto save(PostDetailedDto postDetailedDto) {
        log.debug("Request to save Post : {}", postDetailedDto);
        var post = postDetailedMapper.toEntity(postDetailedDto);
        post = postRepository.save(post);
        return postDetailedMapper.toDto(post);
    }

    @Override
    public Optional<PostDetailedDto> partialUpdate(PostDetailedDto postDetailedDto) {
        log.debug("Request to partially update Post : {}", postDetailedDto);

        return postRepository
            .findById(postDetailedDto.getId())
            .map(
                existingPost -> {
                    postDetailedMapper.partialUpdate(existingPost, postDetailedDto);
                    return existingPost;
                }
            )
            .map(postRepository::save)
            .map(postDetailedMapper::toDto);
    }

    @Override
    public PostResponseList findEnabledByMode(String mode, Long offset, Integer limit) {

        if (!MODE_SORTING_SETTINGS.containsKey(mode)) {
            throw new IllegalArgumentException(String.format("undefined mode parameter %s", mode));
        }

        Pageable pageRequest = new OffsetBasedPageRequest(offset.shortValue(), limit, MODE_SORTING_SETTINGS.get(mode));

        List<PostBriefDto> posts = postViewRepository
            .findByIsActiveAndStatusLessThenInstant(pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now())
            .getContent()
            .stream()
            .map(postViewBriefMapper::toDto)
            .collect(Collectors.toList());

        Long totalCount = postViewRepository.countByIsActiveAndStatusLessThenInstant(1, ModerationStatus.ACCEPTED, Instant.now());

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findEnabledByContent(String content, Long offset, Integer limit) {

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostBriefDto> posts = postViewRepository.findIsActiveAndStatusLessThenInstantAndTextContainingIgnoreCase(
            pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now(), content)
            .stream().map(postViewBriefMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository
            .countIsActivePostsAndStatusLessThenInstantAndTextContainingIgnoreCase(1, ModerationStatus.ACCEPTED, Instant.now(),
                content);

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findEnabledByDate(LocalDate date, Long offset, Integer limit) {

        var startDate = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        var endDate = date.atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC);

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostBriefDto> posts = postViewRepository.findByIsActiveAndStatusBetweenDates(
            pageRequest, 1, ModerationStatus.ACCEPTED, startDate, endDate)
            .stream().map(postViewBriefMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository
            .countByIsActiveAndStatusBetweenDates(1, ModerationStatus.ACCEPTED, startDate, endDate);

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findEnabledByTag(String tag, Long offset, Integer limit) {

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostBriefDto> posts = postViewRepository.findIsActiveAndStatusLessThenInstantAndByTag(
            pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now(), tag)
            .stream().map(postViewBriefMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository.countIsActiveAndStatusLessThenInstantAndByTag(1, ModerationStatus.ACCEPTED, Instant.now(), tag);

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public CalendarResponseList findEnabledWithinYear(Integer year) {

        Map<String, BigInteger> postGroupingByDay =
            postRepository.findIsActiveAndYearAndStatusLessThenInstantGroupingByDays(year, 1, ModerationStatus.ACCEPTED, Instant.now()).stream()
                .collect(
                    Collectors.toMap(o -> ((Date) o[0]).toLocalDate().format(DateTimeFormatter.ISO_DATE), o -> (BigInteger) o[1],
                        (b1, b2) -> b1, TreeMap::new));

        Set<Integer> years = postRepository.findIsActiveAndYearAndStatusLessThenInstantGroupingByYear(1, ModerationStatus.ACCEPTED, Instant.now());

        return new CalendarResponseList(years, postGroupingByDay);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDetailedDto> findOne(Integer id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postDetailedMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

}
