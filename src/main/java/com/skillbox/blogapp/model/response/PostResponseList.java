package com.skillbox.blogapp.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.service.dto.post.PostBriefDto;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;

@Getter
public class PostResponseList implements Serializable {

    private final Long count;

    @JsonIgnoreProperties(value = {"time", "isActive", "moderationStatus", "text", "moderator", "tags", "comments", "votes"})
    private final List<PostBriefDto> posts;

    public PostResponseList(List<PostBriefDto> posts, Long totalCount) {
        this.count = totalCount;
        this.posts = posts;
    }

}
