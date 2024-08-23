package com.example.userservice.auth.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Name must start with an uppercase letter followed by lowercase letters only")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Surname must start with an uppercase letter followed by lowercase letters only")
    private String surname;

    //    @NotBlank
//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email should be valid")
    private String username;

    @NotBlank
    @Pattern(regexp = "^\\+7\\d{3}\\d{3}\\d{4}$", message = "Phone number must be in the format +7 *** *** ****")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$", message = "Password must contain at least 8 characters, including at least 1 digit, 1 uppercase letter, 1 lowercase letter, and 1 special character")
    private String password;
}
