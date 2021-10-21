package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.response.CalendarResponseList;
import com.skillbox.blogapp.service.PostService;
import com.skillbox.blogapp.service.dto.InitDto;
import java.time.LocalDate;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InitController {

    private final PostService postService;
    private final InitDto initDto;

    @GetMapping("/init")
    public ResponseEntity<InitDto> init() {
        return new ResponseEntity<>(initDto, HttpStatus.OK);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponseList> calendar(@RequestParam(value = "year", required = false) Integer year) {
        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }
        return new ResponseEntity<>(postService.findEnabledWithinYear(year), HttpStatus.OK);
    }

}
