package com.manish.jobportal.dto;

import com.manish.jobportal.models.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotNull(message = "First Name is required.")
    private String firstName;

    private String lastName;

    @NotNull(message = "Email is required.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotNull(message = "Password is required.")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!_]).{8,}$",
        message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    private String password;

    @NotNull(message = "User Role is required.")
    private Role role;
}
