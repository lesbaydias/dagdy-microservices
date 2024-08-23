package com.example.habits.model;


import lombok.*;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "habit_daily_progress")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HabitDailyProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progressId")
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "habitId", nullable = false)
    private Habits habit;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean done;

    @Column(nullable = false)
    private Integer dailyNormProcess;
}
