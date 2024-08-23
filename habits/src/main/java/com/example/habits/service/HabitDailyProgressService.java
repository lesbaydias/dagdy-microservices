package com.example.habits.service;

import com.example.habits.model.HabitDailyProgress;
import com.example.habits.model.Habits;
import com.example.habits.repository.HabitDailyProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitDailyProgressService {
    private final HabitDailyProgressRepository habitDailyProgressRepository;
    private final HabitService habitService;

    public List<HabitDailyProgress> getHabitDailyProgress( LocalDate localDate) {
        List<Habits> userHabits = habitService.findByUserUsername("lesbaydias@gmail.com");

        List<HabitDailyProgress> progressList = habitDailyProgressRepository.findHabitDailyProgressesByDate(localDate);

        if (progressList.isEmpty()) {
            throw new RuntimeException("No progress found for the specified date: " + localDate);
        }

        return progressList.stream()
                .filter(progress -> userHabits.stream()
                        .anyMatch(habit -> habit.getUser().getUserId().equals(progress.getHabit().getUser().getUserId())))
                .collect(Collectors.toList());

    }
    public List<HabitDailyProgress> getHabitsInExactlyOneDayInProcessOrDone( LocalDate localDate, boolean done) {
        List<Habits> userHabits = habitService.findByUserUsername("lesbaydias@gmail.com");

        List<HabitDailyProgress> progressList = habitDailyProgressRepository.findHabitDailyProgressesByDate(localDate);

        if(progressList.isEmpty())
            throw new RuntimeException("Habit daily progresses not found");

        return progressList.stream()
                .filter(progress -> userHabits.stream()
                        .anyMatch(habit -> habit.getHabitId().equals(progress.getHabit().getHabitId())))
                .filter(progress -> progress.isDone() == done)
                .collect(Collectors.toList());
    }

    public HabitDailyProgress getHabitInExactlyOneDayAddProcess(Long id){
        HabitDailyProgress habitDailyProgress = habitDailyProgressRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Habit not found")
        );

        if(!habitDailyProgress.getHabit().getUser().getUsername().equals("lesbaydias@gmail.com"))
            throw new RuntimeException("Habit daily progresses don't match");

        if (habitDailyProgress.isDone())
            throw new RuntimeException("Habit already done");

        int newDailyNormProcess = habitDailyProgress.getDailyNormProcess() + 1;
        habitDailyProgress.setDailyNormProcess(newDailyNormProcess);

        if (newDailyNormProcess == habitDailyProgress.getHabit().getDailyNorm())
            habitDailyProgress.setDone(true);

        return habitDailyProgressRepository.save(habitDailyProgress);
    }


}
