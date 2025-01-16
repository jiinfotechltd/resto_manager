package com.jiinfotech.restomanager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class DatabaseBackupService {

    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;
    @Autowired
    private AppProperties appProperties;

    @Scheduled(cron = "0 0 0 * * *")
    public void executeBackup() {
        try {
            String[] command = {"cmd.exe", "/c", appProperties.getBACKUP_SCRIPT_PATH()};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                googleChatNotificationService.sendNotification("Backup completed successfully.\nOutput:\n" + output);
                System.out.println("Backup completed successfully.\nOutput:\n" + output);
            } else {
                googleChatNotificationService.sendNotification("Backup failed. Exit code: " + exitCode + "\nOutput:\n" + output);
                System.err.println("Backup failed. Exit code: " + exitCode + "\nOutput:\n" + output);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            googleChatNotificationService.sendNotification("Unable to run backup script. Error: " + e.getMessage());
        }
    }
}
