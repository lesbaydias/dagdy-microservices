package com.example.userservice.sms.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    private String phoneNumber;
    private String newPassword;
}