package com.jiinfotech.restomanager.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.time.Instant;

public class BaseServices {

    public static String redirect(String url) {
        return "redirect:" + url;
    }

    public static String toJson(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static Timestamp getCurrentTimestamp() {
        Instant now = Instant.now();
        return Timestamp.from(now);
    }

}
