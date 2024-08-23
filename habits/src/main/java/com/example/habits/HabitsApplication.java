package com.example.habits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EntityScan(basePackages = {"com.example.habits.model", "com.example.userservice.user.model"})
@EnableJpaRepositories(basePackages = {"com.example.habits.repository", "com.example.userservice.user.repository"})
public class HabitsApplication {
    public static void main(String[] args) {
        SpringApplication.run(HabitsApplication.class, args);
    }
}


