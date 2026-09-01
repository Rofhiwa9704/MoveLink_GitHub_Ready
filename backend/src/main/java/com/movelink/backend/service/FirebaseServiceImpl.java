package com.movelink.backend.service;

import com.movelink.backend.dto.PushNotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public String sendNotification(PushNotificationRequest request) {

        // Firebase integration will be added after configuring
        // the Firebase Admin SDK and service account.

        return "Firebase notification service is configured. Integration pending.";
    }
}