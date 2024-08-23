package com.example.userservice.sms.repository;

import com.example.userservice.sms.model.Sms;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {
    Sms findSmsByUser_UserId(Long userId);
}
