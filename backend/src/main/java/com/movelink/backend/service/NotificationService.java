package com.movelink.backend.service;

import com.movelink.backend.dto.NotificationRequest;
import com.movelink.backend.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification sendNotification(NotificationRequest request);

    List<Notification> getUserNotifications(Long userId);

    Notification markAsRead(Long notificationId);
}