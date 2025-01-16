package com.jiinfotech.restomanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestoManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestoManagerApplication.class, args);
    }

}
