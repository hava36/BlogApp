package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.TagResponseList;
import com.skillbox.blogapp.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/api/tag")
    public ResponseEntity<TagResponseList> tags(@RequestParam(value = "query", required = false, defaultValue = "") String name) {

        var tagResponse = new TagResponseList(tagService.findViewByNameContainingIgnoreCase(name));
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);

    }

}
