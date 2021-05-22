package com.skillbox.blogapp.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.model.dto.PostDto;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;

@Getter
public class CustomPostResponse implements Serializable {

    private final Integer count;

    @JsonIgnoreProperties(value = {"time", "isActive", "moderationStatus", "text", "moderator", "tags", "comments", "votes"})
    private final List<PostDto> posts;

    public CustomPostResponse(List<PostDto> posts) {
        this.count = posts.size();
        this.posts = posts;
    }

}
