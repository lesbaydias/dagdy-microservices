package com.example.habits.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitDailyProgressRequest {

    private LocalDate date;

    private boolean done;

    private Integer dailyNormProcess;

}
