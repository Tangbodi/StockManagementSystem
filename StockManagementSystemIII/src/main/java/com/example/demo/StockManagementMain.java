package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class StockManagementMain{
    public static void main(String[] args) {
        SpringApplication.run(StockManagementMain.class, args);

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
