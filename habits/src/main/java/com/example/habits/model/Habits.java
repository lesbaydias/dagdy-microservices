package com.example.habits.model;

import com.example.userservice.user.model.Users;
import lombok.*;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "habits")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Habits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitId")
    private Long habitId;

    @NotBlank(message = "Habit name is required")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Habit name must start with an uppercase letter followed by lowercase letters only")
    private String h_name;

    @NotBlank(message = "Description is required")
    @Column(nullable = false)
    private String h_description;

    @Column(nullable = false)
    private boolean habitType;

    @Column(nullable = false)
    private String habitIconUrl;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate endTime;

    @Column(nullable = false)
    private Integer dailyNorm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
