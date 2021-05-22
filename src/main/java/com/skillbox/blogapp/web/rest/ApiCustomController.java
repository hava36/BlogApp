package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.dto.InitDto;
import com.skillbox.blogapp.service.GlobalSettingService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiCustomController {

    private final GlobalSettingService globalSettingService;
    private final InitDto initDto;

    public ApiCustomController(
        GlobalSettingService globalSettingService, InitDto initDto) {
        this.globalSettingService = globalSettingService;
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

}
