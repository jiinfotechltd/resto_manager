package com.jiinfotech.restomanager.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${google.chat.webhook.url}") 
    private final String googleChannelUrl = null;

    @Value("${backup.script.path}")
    private final String BACKUP_SCRIPT_PATH = null;

    public String getGoogleChannelUrl() {
        return googleChannelUrl;
    }

    public String getBACKUP_SCRIPT_PATH() {
        return BACKUP_SCRIPT_PATH;
    }
}