package com.example.userservice.sms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryRequest {
    private String phoneNumber;
}