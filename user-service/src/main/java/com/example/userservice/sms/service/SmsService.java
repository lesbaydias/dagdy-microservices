package com.example.userservice.sms.service;

import com.example.userservice.sms.model.Sms;
import com.example.userservice.sms.repository.SmsRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsRepository smsRepository;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String message) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                message
        ).create();
    }
    Sms findSmsByUser_UserId(Long userId) {
        return smsRepository.findSmsByUser_UserId(userId);
    }
    void save(Sms sms) {
        smsRepository.save(sms);
    }
    @Transactional
    public void delete(Sms sms) {
        smsRepository.delete(sms);
    }
}
