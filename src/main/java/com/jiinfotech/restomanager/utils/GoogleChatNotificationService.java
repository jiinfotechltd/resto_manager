package com.jiinfotech.restomanager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GoogleChatNotificationService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AppProperties appProperties;

    public void sendNotification(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        String jsonBody = String.format("{\"text\": \"%s\"}", message);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(appProperties.getGoogleChannelUrl(), HttpMethod.POST, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Notification sent successfully!");
        } else {
            System.err.println("Failed to send notification: " + response.getBody());
        }
    }

    public void sendFileToGoogleChat(String backupFilePath) {
        try {
            File backupFile = new File(backupFilePath);
            if (!backupFile.exists()) {
                sendNotification("Backup file not found: " + backupFilePath);
                return;
            }
            URL url = new URL(appProperties.getGoogleChannelUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonPayload = String.format("{ \"text\": \"Backup File: %s\", \"attachments\": [{ \"fileName\": \"%s\", \"contentType\": \"application/sql\", \"file\": \"%s\" }] }",
                    backupFile.getName(), backupFile.getName(), encodeFileToBase64(backupFile));
            connection.getOutputStream().write(jsonPayload.getBytes());
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                sendNotification("Backup file sent successfully to Google Chat.");
            } else {
                sendNotification("Failed to send backup file. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendNotification("Error sending file to Google Chat: " + e.getMessage());
        }
    }

    private String encodeFileToBase64(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);
            return java.util.Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}