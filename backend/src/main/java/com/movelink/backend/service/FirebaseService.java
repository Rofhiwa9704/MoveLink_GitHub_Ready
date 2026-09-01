package com.movelink.backend.service;

import com.movelink.backend.dto.PushNotificationRequest;

public interface FirebaseService {

    String sendNotification(PushNotificationRequest request);
}