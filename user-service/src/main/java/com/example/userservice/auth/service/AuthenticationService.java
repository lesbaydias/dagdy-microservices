package com.example.userservice.auth.service;


import com.example.commonlib.exception.ServiceException;
import com.example.userservice.auth.dto.JwtResponse;
import com.example.userservice.auth.dto.LoginRequest;
import com.example.userservice.auth.dto.RegisterRequest;
import com.example.userservice.user.model.Users;
import com.example.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public Users register(RegisterRequest registerRequest) {
        if(userRepository.findUsersByUsername(registerRequest.getUsername()).isPresent() ||
                userRepository.findUsersByPhoneNumber(registerRequest.getPhoneNumber()).isPresent())
            throw new ServiceException("Duplicate email or phone number exists", HttpStatus.BAD_REQUEST);
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Users user = Users.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .surname(registerRequest.getSurname())
                .build();

        return userRepository.save(user);
    }

    public ResponseEntity<?>  login(LoginRequest loginRequest) {
        Users users = userRepository.findUsersByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new ServiceException("User not found", HttpStatus.NOT_FOUND)
        );

        if(!passwordEncoder.matches(loginRequest.getPassword(), users.getPassword()))
            throw new ServiceException("Wrong password", HttpStatus.BAD_REQUEST);

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER"))));
        if (authenticate.isAuthenticated()) {
            return ResponseEntity.ok(new JwtResponse(generateToken(loginRequest.getUsername())));
        } else {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        }

    }
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }
}