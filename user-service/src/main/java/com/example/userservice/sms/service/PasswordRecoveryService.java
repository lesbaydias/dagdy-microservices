package com.example.userservice.sms.service;

import com.example.commonlib.exception.ServiceException;
import com.example.userservice.user.model.Users;
import com.example.userservice.sms.dto.ResetPasswordRequest;
import com.example.userservice.sms.dto.VerifyCodeRequest;
import com.example.userservice.sms.dto.PasswordRecoveryRequest;
import com.example.userservice.sms.model.Sms;
import com.example.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final SmsService smsService;
    private final UserRepository userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendRecoveryCode(PasswordRecoveryRequest request) {
        Users user = userService.findUsersByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new ServiceException(request.getPhoneNumber(), HttpStatus.NOT_FOUND)
        );
        Sms sms = smsService.findSmsByUser_UserId(user.getUserId());
        if (sms != null)
            smsService.delete(sms);

        String recoveryCode = generateRecoveryCode();

        sms = createSmsEntry(user, recoveryCode);
        smsService.save(sms);

        sendSmsNotification(request.getPhoneNumber(), recoveryCode);
    }


    private Sms createSmsEntry(Users user, String code) {
        return Sms.builder()
                .sms(code)
                .user(user)
                .build();
    }

    private void sendSmsNotification(String phoneNumber, String code) {
        String message = "Your recovery code is: " + code;
        smsService.sendSms(phoneNumber, message);
    }


    public void verifyCode(VerifyCodeRequest request) {
        Users user = userService.findUsersByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new ServiceException(request.getPhoneNumber(), HttpStatus.NOT_FOUND)
        );
        Sms sms = smsService.findSmsByUser_UserId(user.getUserId());
        if (sms == null)
            throw new ServiceException("Your sms did not found! ",HttpStatus.NOT_FOUND);

        if(!sms.getSms().equalsIgnoreCase(request.getCode()))
            throw new ServiceException("Your sms did not match! ",HttpStatus.BAD_REQUEST);
    }




    public void resetPassword(ResetPasswordRequest request) {
        Users user = userService.findUsersByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new ServiceException(request.getPhoneNumber(), HttpStatus.NOT_FOUND)
        );
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);
    }

    private String generateRecoveryCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}
