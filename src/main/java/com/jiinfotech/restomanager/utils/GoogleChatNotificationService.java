package com.jiinfotech.restomanager.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleChatNotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    private static final String WEBHOOK_URL = "https://chat.googleapis.com/v1/spaces/AAAAkd0OxGk/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=yufY_fSZ9d2fJEecD92rzbqp5ertMfRvnQbOGX_JpFQ";

    public void sendNotification(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String jsonBody = String.format("{\"text\": \"%s\"}", message);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(WEBHOOK_URL, HttpMethod.POST, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Notification sent successfully!");
        } else {
            System.err.println("Failed to send notification: " + response.getBody());
        }
    }
}

