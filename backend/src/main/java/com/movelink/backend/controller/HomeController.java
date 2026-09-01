package com.movelink.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {

        return Map.of(
                "application", "MoveLink Backend",
                "status", "Running",
                "time", LocalDateTime.now(),
                "version", "1.0.0"
        );
    }
}