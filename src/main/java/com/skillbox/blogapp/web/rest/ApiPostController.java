package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.CustomPostResponse;
import com.skillbox.blogapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final PostService postService;

    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/post")
    public ResponseEntity<CustomPostResponse> posts(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "recent") String mode) {
        return new ResponseEntity<>(postService.findActivePostByMode(offset, limit, mode), HttpStatus.OK);
    }

}
