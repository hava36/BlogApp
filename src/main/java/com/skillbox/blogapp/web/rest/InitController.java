package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.dto.InitDto;
import com.skillbox.blogapp.model.response.CalendarResponseList;
import com.skillbox.blogapp.service.GlobalSettingService;
import com.skillbox.blogapp.service.PostService;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InitController {

    private final GlobalSettingService globalSettingService;
    private final PostService postService;
    private final InitDto initDto;

    public InitController(
        GlobalSettingService globalSettingService, PostService postService, InitDto initDto) {
        this.globalSettingService = globalSettingService;
        this.postService = postService;
        this.initDto = initDto;
    }

    @GetMapping("/init")
    public ResponseEntity<InitDto> init() {
        return new ResponseEntity<>(initDto, HttpStatus.OK);
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> settings() {
        return new ResponseEntity<>(globalSettingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/calendar")
    public ResponseEntity<CalendarResponseList> calendar(@RequestParam(value = "year", required = false) Integer year) {
        if (Objects.isNull(year)) {
            year = LocalDate.now().getYear();
        }
        return new ResponseEntity<>(postService.findEnabledWithinYear(year), HttpStatus.OK);
    }

}
