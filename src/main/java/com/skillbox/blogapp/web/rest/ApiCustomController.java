package com.skillbox.blogapp.web.rest;

import com.skillbox.blogapp.model.dto.GlobalSettingDto;
import com.skillbox.blogapp.service.GlobalSettingService;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiCustomController {

    @Autowired
    private GlobalSettingService globalSettingService;

    @GetMapping("/settings")
    public ResponseEntity<Map<String, String>> settings() {

        Map<String, String> result = globalSettingService.findAll().stream()
            .collect(Collectors
                .toMap(GlobalSettingDto::getCode, GlobalSettingDto::getValue, (s1, s2) -> s1,
                    TreeMap::new));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
