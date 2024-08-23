package com.example.habits.repository;

import com.example.habits.model.HabitDailyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HabitDailyProgressRepository extends JpaRepository<HabitDailyProgress, Long> {

    List<HabitDailyProgress> findHabitDailyProgressesByDate(LocalDate date);

}
