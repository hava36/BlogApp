package com.skillbox.blogapp.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skillbox.blogapp.model.dto.PostDto;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;

@Getter
public class PostResponse implements Serializable {

    private Integer count;

    @JsonIgnoreProperties(value = {"time", "isActive", "moderationStatus", "text", "moderator"})
    private List<PostDto> posts;

    public PostResponse(List<PostDto> posts) {
        this.count = posts.size();
        this.posts = posts;
    }

}
