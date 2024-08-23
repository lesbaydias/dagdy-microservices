package com.example.habits.controller;
import com.example.habits.dto.CreateHabitRequest;
import com.example.habits.model.HabitDailyProgress;
import com.example.habits.model.Habits;
import com.example.habits.service.HabitDailyProgressService;
import com.example.habits.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habits")
public class HabitController {
    private static final Logger log = LoggerFactory.getLogger(HabitController.class);
    private final HabitService habitService;
    private final HabitDailyProgressService habitDailyProgressService;

    @PostMapping("/create-habit")
    public ResponseEntity<String> createHabit(
            @RequestParam("hName") String hName,
            @RequestParam("description") String description,
            @RequestParam("type") boolean type,
            @RequestParam("habitIcon") MultipartFile habitIcon,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime,
            @RequestParam("dailyNorm") Integer dailyNorm)
    {
        log.info("Createeeeeee habit {}", hName);
        CreateHabitRequest createHabitRequest = CreateHabitRequest.builder()
                .hName(hName)
                .description(description)
                .habitType(type)
                .habitIcon(habitIcon)
                .startTime(startTime)
                .endTime(endTime)
                .dailyNorm(dailyNorm)
                .build();
        return habitService.createHabit(createHabitRequest);
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        return ResponseEntity.ok(habitService.deleteFile(fileName));
    }

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        byte[] fileContent = habitService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileContent);
    }

    @PostMapping("/update-file")
    public ResponseEntity<String> updateFile(
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(habitService.updateFile(fileName, file));
    }


    @GetMapping("/list-of-habits")
    public List<Habits> listOfHabits( boolean type){
        return habitService.getListOfHabits(type);
    }

    @GetMapping("/habit/{id}")
    public Habits getHabitById(@PathVariable Long  id) {
        return habitService.getHabitById(id);
    }


    @GetMapping("/habits-in-exactly-one-day")
    public List<HabitDailyProgress> habitsInExactlyOneDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return habitDailyProgressService.getHabitDailyProgress( date);
    }

    @GetMapping("habits-in-exactly-one-day/in-process-or-done")
    public List<HabitDailyProgress> habitsInExactlyOneDayInProcessOrDone(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam boolean done)
    {
        return habitDailyProgressService.getHabitsInExactlyOneDayInProcessOrDone(date, done);
    }

    @PostMapping("habits-in-exactly-one-day/add-process/{id}")
    public HabitDailyProgress habitInExactlyOneDayAddProcess(
            @PathVariable int id) {
        return habitDailyProgressService.getHabitInExactlyOneDayAddProcess( (long) id);
    }

    @GetMapping("/get")
    public String get(){
        return "get";
    }
}
