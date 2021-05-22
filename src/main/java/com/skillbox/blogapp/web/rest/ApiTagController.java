package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.CustomTagResponse;
import com.skillbox.blogapp.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTagController {

    private final TagService tagService;

    public ApiTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/api/tag")
    public ResponseEntity<CustomTagResponse> tags(@RequestParam(value = "query", required = false, defaultValue = "") String name) {

        var tagResponse = new CustomTagResponse(tagService.findViewByNameContainingIgnoreCase(name));
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);

    }

}
