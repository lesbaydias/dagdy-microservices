package com.example.habits.service;

import com.example.habits.config.AwsService;
import com.example.habits.dto.CreateHabitRequest;
import com.example.habits.model.Habits;
import com.example.habits.repository.HabitRepository;
import com.example.userservice.user.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final AwsService s3Service;


    public ResponseEntity<String> createHabit(CreateHabitRequest createHabitRequest) {
        String iconUrl = s3Service.uploadFile(createHabitRequest.getHabitIcon());

        Users user = null;

        Habits habit = Habits.builder()
                .h_name(createHabitRequest.getHName())
                .h_description(createHabitRequest.getDescription())
                .habitType(createHabitRequest.isHabitType())
                .habitIconUrl(iconUrl)
                .startTime(createHabitRequest.getStartTime())
                .endTime(createHabitRequest.getEndTime())
                .dailyNorm(createHabitRequest.getDailyNorm())
                .user(user)
                .build();

        habitRepository.save(habit);

        return ResponseEntity.ok("Habit created successfully with id: " + habit.getHabitId());
    }


    public String deleteFile(String fileName) {
        s3Service.deleteFile(fileName);

        Optional<Habits> habit = habitRepository.findByHabitIconUrl(fileName);
        habit.ifPresent(habitRepository::delete);

        return fileName + " removed.";
    }

    public byte[] downloadFile(String fileName) {
        return s3Service.downloadFile(fileName);
    }



    public String updateFile(String fileName, MultipartFile file) {

        s3Service.deleteFile(fileName);

        String newFileName = s3Service.updateFile(fileName, file);

        Optional<Habits> habit = habitRepository.findByHabitIconUrl(fileName);
        if (habit.isPresent()) {
            Habits updatedHabit = habit.get();
            updatedHabit.setHabitIconUrl(newFileName);
            habitRepository.save(updatedHabit);
        }

        return newFileName;
    }


    public List<Habits> getListOfHabits( boolean habitType){
        return habitRepository.findHabitsByUserUsernameAndHabitType("lesbaydias@gmail.com",  habitType).orElseThrow(
                () -> new RuntimeException("Habits not found")
        );
    }

    public Habits getHabitById( Long habitId) {
        return habitRepository.findHabitsByHabitIdAndUser_Username(habitId, "lesbaydias@gmail.com").orElseThrow(
                () -> new RuntimeException("Habits not found")
        );
    }

    public List<Habits> findByUserUsername(String userUsername) {
        return habitRepository.findByUserUsername(userUsername).orElseThrow(
                () -> new RuntimeException("Habits not found")
        );
    }


}

