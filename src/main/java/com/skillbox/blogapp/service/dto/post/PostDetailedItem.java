package com.skillbox.blogapp.service.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.blogapp.model.entity.enumeration.ModerationStatus;
import com.skillbox.blogapp.service.dto.PostCommentDto;
import com.skillbox.blogapp.service.dto.PostVoteDto;
import com.skillbox.blogapp.service.dto.TagDto;
import com.skillbox.blogapp.service.dto.UserDto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;

@Getter
@Setter
public class PostDetailedItem implements Serializable {

    private static final int MAX_COUNT_OF_ANNOUNCE = 150;

    private Integer id;

    @NotNull
    @JsonProperty("active")
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
    @JsonIgnore
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
    private Set<PostCommentDto> comments;

    @JsonIgnore
    private Set<PostVoteDto> votes;

    public void setAnnounce(String text) {
        String textWithoutTags = Jsoup.parse(text).text();
        if (textWithoutTags.length() > MAX_COUNT_OF_ANNOUNCE) {
            this.announce = textWithoutTags.substring(0, 150) + "...";
        } else {
            this.announce = textWithoutTags;
        }
    }

}
