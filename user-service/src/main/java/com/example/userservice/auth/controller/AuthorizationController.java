package com.example.userservice.auth.controller;

import com.example.userservice.auth.dto.LoginRequest;
import com.example.userservice.auth.dto.RegisterRequest;
import com.example.userservice.auth.service.AuthenticationService;
import com.example.userservice.user.model.Users;
import com.example.userservice.sms.dto.PasswordRecoveryRequest;
import com.example.userservice.sms.service.PasswordRecoveryService;
import com.example.userservice.sms.dto.ResetPasswordRequest;
import com.example.userservice.sms.dto.VerifyCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-service/auth")
public class AuthorizationController {
    private final AuthenticationService authenticationService;
    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Users register(@Valid @RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }


    @PostMapping("/send-code")
    public ResponseEntity<String> sendRecoveryCode(
            @RequestBody PasswordRecoveryRequest request
    ) {
        passwordRecoveryService.sendRecoveryCode(request);

        return ResponseEntity.ok("Recovery code sent");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest request) {
        passwordRecoveryService.verifyCode(request);
        return ResponseEntity.ok("Code verified");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordRecoveryService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}
