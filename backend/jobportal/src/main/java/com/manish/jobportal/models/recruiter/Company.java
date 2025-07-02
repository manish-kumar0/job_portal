package com.manish.jobportal.models.recruiter;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.manish.jobportal.models.DialCode;
import com.manish.jobportal.models.Documents;
import com.manish.jobportal.models.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "companies")
public class Company {

    @Id
    private String id;

    private String companyName;
    private String industryType;
    private String businessEmail;
    private String description;
    private String website;
    private String phoneNumber;

    private DialCode dialCode;

    private String address;

    private Map<String, String> socialMedia;

    private Documents logo;

    @Indexed(unique = true)
    private String userId;

    private int hired;
    private long activeCandidate;
    private long interviewed;

    private Status status = Status.PENDING;

    // Explicitly define getter for userId (required if Lombok fails)
    public String getUserId() {
        return userId;
    }

    // Explicitly define setter for status (fixes company.setStatus issue)
    public void setStatus(Status status) {
        this.status = status;
    }
}
