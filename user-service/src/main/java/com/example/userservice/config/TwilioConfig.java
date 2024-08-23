package com.example.userservice.config;

import com.twilio.Twilio;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@Getter
@PropertySource("classpath:application.properties")
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;


    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

}
