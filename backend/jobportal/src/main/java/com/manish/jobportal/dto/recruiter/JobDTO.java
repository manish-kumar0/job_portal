package com.manish.jobportal.dto.recruiter;

import java.time.LocalDate;

import com.manish.jobportal.models.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private String jobTitle;

    private String jobType;

    private String category;

    private String experienceLevel;

    private String salaryRange;

    private String location;

    private String jobDescription;

    private String responsibilities;

    private String requirements;

    private String niceToHave;

    private String educationRequirement;

    private String requiredSkills;

    private String benefitsAndPerks;

    private LocalDate deadline;

    private Status status;
}
