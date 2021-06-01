package com.skillbox.blogapp.model.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.model.dto.TagDto;
import com.skillbox.blogapp.model.dto.UserDto;
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
import org.jsoup.Jsoup;

/**
 * A DTO for the {@link Post} entity.
 */
@Getter
@Setter
public class PostBriefDto implements Serializable {

    private static final int MAX_COUNT_OF_ANNOUNCE = 150;

    private Integer id;

    @NotNull
    private Integer isActive;

    @NotNull
    private ModerationStatus moderationStatus;

    @NotNull
    @JsonIgnore
    private Instant time;

    @NotNull
    private Long timestamp;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String announce;

    @NotNull
    private Long likeCount;

    @NotNull
    private Long dislikeCount;

    @NotNull
    private Integer commentCount;

    @NotNull
    private Integer viewCount;

    @JsonIgnore
    @JsonIgnoreProperties(value = {"isModerator", "regTime", "email", "password", "code", "photo",
        "posts"})
    private UserDto moderator;

    @NotNull
    @JsonIgnoreProperties(value = {"isModerator", "regTime", "email", "password", "code", "photo",
        "posts"})
    private UserDto user;

    @JsonIgnoreProperties(value = {"id", "weight"})
    private Set<TagDto> tags;

    @JsonIgnoreProperties(value = {"parent", "post"})
    private Set<PostComment> comments;

    @JsonIgnore
    private Set<PostVote> votes;

    public void setAnnounce(String text) {
        String textWithoutTags = Jsoup.parse(text).text();
        if (textWithoutTags.length() > MAX_COUNT_OF_ANNOUNCE) {
            this.announce = textWithoutTags.substring(0, 150) + "...";
        } else {
            this.announce = textWithoutTags;
        }
    }

}