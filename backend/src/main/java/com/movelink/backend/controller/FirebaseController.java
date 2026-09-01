package com.movelink.backend.controller;

import com.movelink.backend.dto.PushNotificationRequest;
import com.movelink.backend.service.FirebaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {

    private final FirebaseService firebaseService;

    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestBody PushNotificationRequest request) {

        return ResponseEntity.ok(
                firebaseService.sendNotification(request)
        );
    }
}