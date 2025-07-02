package com.manish.jobportal.dto;

import com.manish.jobportal.models.Role;
import com.manish.jobportal.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String experience;
    private String message;
    private String profileImage;

    public static AuthResponseDTO createResponse(User user, String profileImageUrl, String message) {
        return AuthResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .experience(user.getExperience())
                .message(message)
                .profileImage(profileImageUrl)
                .build();
    }
}
