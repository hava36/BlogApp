package com.skillbox.blogapp.service.dto;

import com.skillbox.blogapp.model.entity.PostVote;
import com.skillbox.blogapp.service.dto.post.PostBriefDto;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link PostVote} entity.
 */
@Getter
@Setter
public class PostVoteDto implements Serializable {

    private Integer id;

    @NotNull
    private Instant time;

    @NotNull
    private Integer value;

    @NotNull
    private UserDto user;

    @NotNull
    private PostBriefDto post;

}
