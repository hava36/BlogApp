package com.skillbox.blogapp.model.dto;

import com.skillbox.blogapp.model.entity.Post;
import com.skillbox.blogapp.model.entity.User;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link User} entity.
 */
@Getter
@Setter
public class UserDto implements Serializable {

    private Integer id;

    @NotNull
    private Integer isModerator;

    private Instant regTime;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String code;

    private String photo;

    private Set<Post> posts;

}
