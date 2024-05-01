package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CaptureApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptureApplication.class, args);
    }

}
