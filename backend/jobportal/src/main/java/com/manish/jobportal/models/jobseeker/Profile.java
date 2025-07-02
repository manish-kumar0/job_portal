package com.manish.jobportal.models.jobseeker;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.manish.jobportal.models.Address;
import com.manish.jobportal.models.DialCode;
import com.manish.jobportal.models.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profile")
public class Profile {

    @Id
    private String id;

    private Documents profileImage;

    private String title;

    private String bio;

    private DialCode dialCode;

    private String phoneNumber;

    private Address address;

    private List<String> socialLinks;

    @Indexed(unique = true)
    private String userId;

    // Optional: Add explicit getter for profileImage if needed
    public Documents getProfileImage() {
        return profileImage;
    }
}
