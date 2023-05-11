package com.example.mobile_banking_api.api.notification;

import com.example.mobile_banking_api.api.notification.web.CreateNotificationDto;
import com.example.mobile_banking_api.api.notification.web.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService{
    private final RestTemplate restTemplate;
    @Autowired
    public NotificationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Value("${onesignal.app-id}")
    private String appId;
    @Value("${onesignal.rest-api-key}")
    private String RestApi;


    @Override
    public boolean pushNotification(CreateNotificationDto notificationDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept", "application/json");
        httpHeaders.set("Authorization", "Basic "+RestApi);
        httpHeaders.set("content-type", "application/json");

        NotificationDto body = NotificationDto.builder()
                .appId(appId)
                .includedSegments(notificationDto.includedSegments())
                .contents(notificationDto.contents())
                .build();
        HttpEntity<NotificationDto> reqBody=new HttpEntity<>(body,httpHeaders);


       ResponseEntity<?> response = restTemplate.postForEntity("https://onesignal.com/api/v1/notifications",
               reqBody, Map.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        return response.getStatusCode()== HttpStatus.OK;
    }
}
