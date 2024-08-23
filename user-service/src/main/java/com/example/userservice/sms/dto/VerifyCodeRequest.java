package com.example.userservice.sms.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {
    private String phoneNumber;
    private String code;
}