package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.service.GlobalSettingService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettingsController {

    private final GlobalSettingService globalSettingService;

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> getSettings() {
        return new ResponseEntity<>(globalSettingService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> updateSettings(@RequestBody Map<String, Boolean> settings) {
        return new ResponseEntity<>(globalSettingService.updateSettings(settings), HttpStatus.OK);
    }
}
