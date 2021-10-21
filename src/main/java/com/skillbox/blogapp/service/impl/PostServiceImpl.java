package com.skillbox.blogapp.service.impl;

import static com.skillbox.blogapp.config.Constants.MODE_SORTING_SETTINGS;
import static com.skillbox.blogapp.config.Constants.MY_POSTS_STATUS_PARAMETERS;

import com.skillbox.blogapp.model.entity.enums.ModerationStatus;
import com.skillbox.blogapp.model.response.CalendarResponseList;
import com.skillbox.blogapp.model.response.PostResponseList;
import com.skillbox.blogapp.repository.PostRepository;
import com.skillbox.blogapp.repository.PostViewRepositoryReadOnly;
import com.skillbox.blogapp.repository.domain.OffsetBasedPageRequest;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.UserService;
import com.skillbox.blogapp.service.dto.UserDto;
import com.skillbox.blogapp.service.dto.post.PostDetailedItem;
import com.skillbox.blogapp.service.dto.post.PostResponseListItem;
import com.skillbox.blogapp.service.mapper.PostDetailedMapper;
import com.skillbox.blogapp.service.mapper.PostViewBriefMapper;
import java.math.BigInteger;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final UserService userService;

    private final PostRepository postRepository;

    private final PostDetailedMapper postDetailedMapper;

    private final PostViewRepositoryReadOnly postViewRepository;

    private final PostViewBriefMapper postListItemMapper;

    @Override
    public PostDetailedItem save(PostDetailedItem postDetailedItem) {
        log.debug("Request to save Post : {}", postDetailedItem);
        var post = postDetailedMapper.toEntity(postDetailedItem);
        post = postRepository.save(post);
        return postDetailedMapper.toDto(post);
    }

    @Override
    public Optional<PostDetailedItem> partialUpdate(PostDetailedItem postDetailedItem) {
        log.debug("Request to partially update Post : {}", postDetailedItem);

        return postRepository
            .findById(postDetailedItem.getId())
            .map(
                existingPost -> {
                    postDetailedMapper.partialUpdate(existingPost, postDetailedItem);
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

        List<PostResponseListItem> posts = postViewRepository
            .findByIsActiveAndStatusLessThenInstant(pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now())
            .getContent()
            .stream()
            .map(postListItemMapper::toDto)
            .collect(Collectors.toList());

        Long totalCount = postViewRepository.countByIsActiveAndStatusLessThenInstant(1, ModerationStatus.ACCEPTED, Instant.now());

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findEnabledByContent(String content, Long offset, Integer limit) {

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostResponseListItem> posts = postViewRepository.findIsActiveAndStatusLessThenInstantAndTextContainingIgnoreCase(
            pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now(), content)
            .stream().map(postListItemMapper::toDto).collect(Collectors.toList());

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

        List<PostResponseListItem> posts = postViewRepository.findByIsActiveAndStatusBetweenDates(
            pageRequest, 1, ModerationStatus.ACCEPTED, startDate, endDate)
            .stream().map(postListItemMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository
            .countByIsActiveAndStatusBetweenDates(1, ModerationStatus.ACCEPTED, startDate, endDate);

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findEnabledByTag(String tag, Long offset, Integer limit) {

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostResponseListItem> posts = postViewRepository.findIsActiveAndStatusLessThenInstantAndByTag(
            pageRequest, 1, ModerationStatus.ACCEPTED, Instant.now(), tag)
            .stream().map(postListItemMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository.countIsActiveAndStatusLessThenInstantAndByTag(1, ModerationStatus.ACCEPTED, Instant.now(), tag);

        return new PostResponseList(posts, totalCount);
    }

    @Override
    public PostResponseList findUserPostsByStatus(String status, Long offset, Integer limit) {

        Optional<UserDto> optionalUserDto = userService.findAuthenticatedUser();

        if (optionalUserDto.isEmpty()) {
            return new PostResponseList(new ArrayList<>(), 0L);
        }

        Map<String, Object> parameters = MY_POSTS_STATUS_PARAMETERS.get(status);

        Integer isActive = Integer.valueOf(String.valueOf(parameters.get("isActive")));
        Collection<String> statuses = (Collection<String>) parameters.get("statuses");

        UserDto userDto = optionalUserDto.get();

        Pageable pageRequest = new OffsetBasedPageRequest(offset, limit, Sort.by(Direction.DESC, "time"));

        List<PostResponseListItem> posts = postViewRepository.findIsActiveAndByStatusesAndByUserId(
            pageRequest, isActive, statuses, userDto.getId())
            .stream().map(postListItemMapper::toDto).collect(Collectors.toList());

        Long totalCount = postViewRepository.countIsActiveAndStatusLessThenInstantAndByUserId(
            isActive, statuses, userDto.getId());

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
    public Optional<PostDetailedItem> findOne(Integer id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postDetailedMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

}
