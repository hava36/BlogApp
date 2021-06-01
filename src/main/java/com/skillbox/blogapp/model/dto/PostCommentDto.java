package com.skillbox.blogapp.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.model.dto.post.PostBriefDto;
import com.skillbox.blogapp.model.entity.PostComment;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link PostComment} entity.
 */
@Getter
@Setter
public class PostCommentDto implements Serializable {

    private Integer id;

    @NotNull
    private Instant time;

    @NotNull
    private String text;

    private PostCommentDto parent;

    @JsonIgnoreProperties(value = {"password", "isModerator", "regTime", "email", "code", "posts"})
    private UserDto user;

    private PostBriefDto post;

}
