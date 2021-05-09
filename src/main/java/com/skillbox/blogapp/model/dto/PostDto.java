package com.skillbox.blogapp.model.dto;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.model.entity.PostComment;
import com.skillbox.blogapp.model.entity.PostVote;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link Post} entity.
 */
@Getter
@Setter
public class PostDto implements Serializable {

    private Integer id;

    @NotNull
    private Integer isActive;

    @NotNull
    private ModerationStatus moderationStatus;

    @NotNull
    private Instant time;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private Integer viewCount;

    private UserDto moderator;

    @NotNull
    private UserDto user;

    private Set<TagDto> tags;

    private Set<PostComment> comments;

    private Set<PostVote> votes;

}
