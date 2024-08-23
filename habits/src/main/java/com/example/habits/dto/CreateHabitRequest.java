package com.example.habits.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateHabitRequest {
    private String hName;
    private String description;
    private boolean habitType;
    private MultipartFile habitIcon;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer dailyNorm;
}
