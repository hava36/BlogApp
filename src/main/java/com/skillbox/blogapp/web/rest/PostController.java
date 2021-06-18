package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.PostResponseList;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.dto.post.PostDetailedDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostResponseList> searchByMode(
        @RequestParam(required = false, defaultValue = "0") Long offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "recent") String mode) {

        return new ResponseEntity<>(postService.findEnabledByMode(mode, offset, limit), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailedDto> findPostById(@PathVariable("id") Integer id) {
        Optional<PostDetailedDto> optionalPostDto = postService.findOne(id);
        return optionalPostDto.map(postDetailedDto -> new ResponseEntity<>(postDetailedDto, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponseList> searchByQuery(
        @RequestParam(required = false, defaultValue = "0") Long offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "") String query) {

        if (query.isBlank()) {
            return new ResponseEntity<>(postService.findEnabledByMode("recent", offset, limit), HttpStatus.OK);
        }

        return new ResponseEntity<>(postService.findEnabledByContent(query, offset, limit), HttpStatus.OK);

    }

    @GetMapping("/byDate")
    public ResponseEntity<PostResponseList> findByDate(
        @RequestParam(required = false, defaultValue = "0") Long offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(name = "date") String datePresentation) {

        var date = LocalDate.parse(datePresentation, DateTimeFormatter.ISO_LOCAL_DATE);
        return new ResponseEntity<>(postService.findEnabledByDate(date, offset, limit), HttpStatus.OK);
    }

    @GetMapping("/byTag")
    public ResponseEntity<PostResponseList> findByTag(
        @RequestParam(required = false, defaultValue = "0") Long offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(name = "tag") String tag) {

        return new ResponseEntity<>(postService.findEnabledByTag(tag, offset, limit), HttpStatus.OK);

    }


}
