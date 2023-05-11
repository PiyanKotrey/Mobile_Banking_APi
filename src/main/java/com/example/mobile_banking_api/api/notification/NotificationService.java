package com.example.mobile_banking_api.api.notification;

import com.example.mobile_banking_api.api.notification.web.CreateNotificationDto;
import com.example.mobile_banking_api.api.notification.web.NotificationDto;

public interface NotificationService {
    boolean pushNotification(CreateNotificationDto notificationDto);
}
