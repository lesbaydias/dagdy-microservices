package com.example.habits.repository;

import com.example.habits.model.Habits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habits, Long> {
    Optional<Habits> findByHabitIconUrl(String habitIcon);

    Optional<List<Habits>> findHabitsByUserUsernameAndHabitType(String username, boolean habitType);

    Optional<Habits> findHabitsByHabitIdAndUser_Username(Long habitId, String username);

    Optional<List<Habits>> findByUserUsername(String username);

}
