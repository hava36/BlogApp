package com.skillbox.blogapp.model.dto;

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

    private UserDto user;

    private PostDto post;

}
