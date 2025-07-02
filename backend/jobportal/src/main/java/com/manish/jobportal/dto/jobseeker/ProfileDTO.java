package com.manish.jobportal.dto.jobseeker;

import java.util.List;

import com.manish.jobportal.dto.AddressDTO;
import com.manish.jobportal.dto.DialCodeDTO;
import com.manish.jobportal.dto.DocumentsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    
    private DocumentsDTO profileImage;

    private String title;

    private String bio;

    private DialCodeDTO dialCode;

    private String phoneNumber;

    private AddressDTO address;

    private List<String> socialLinks;
}
