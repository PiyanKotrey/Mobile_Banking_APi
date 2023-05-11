package com.example.mobile_banking_api.api.notification.web;

import com.example.mobile_banking_api.api.notification.NotificationService;
import com.example.mobile_banking_api.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationRestController {
    private final NotificationService notificationService;
    @GetMapping
    public BaseRest<?> pushNotification(@RequestBody CreateNotificationDto notificationDto){
        notificationService.pushNotification(notificationDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Notification has been push")
                .timestamp(LocalDateTime.now())
                .data(notificationDto.contents())
                .build();
    }
}
