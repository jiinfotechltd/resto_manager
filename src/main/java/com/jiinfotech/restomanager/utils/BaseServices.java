package com.jiinfotech.restomanager.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseServices {

    public static String redirect(String url) {
        return "redirect:" + url;
    }

    public static String toJson(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
